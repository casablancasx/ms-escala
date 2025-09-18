package br.gov.agu.nutec.msescala.service;

import br.gov.agu.nutec.msescala.entity.*;
import br.gov.agu.nutec.msescala.repository.AvaliadorRepository;
import br.gov.agu.nutec.msescala.repository.EscalaRepository;
import br.gov.agu.nutec.msescala.repository.PautistaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class EscalaService {

    private final AvaliadorRepository avaliadorRepository;
    private final EscalaRepository escalaRepository;
    private final PautistaRepository pautistaRepository;

    private final CadastrarTarefaService cadastrarTarefaService;


    @Transactional
    public void escalarAvaliadores(final PautaEntity pauta) {

        List<AvaliadorEntity> avaliadores = avaliadorRepository.buscarAvaliadoresDisponveis(pauta.getData());
        var avaliadorSelecionado = selecionarAvaliador(avaliadores);
        escalarAvaliadorNaPauta(avaliadorSelecionado, pauta);

        for (AudienciaEntity audiencia : pauta.getAudiencias()) {
            cadastrarTarefaService.cadastrarTarefaSapiens(audiencia, avaliadorSelecionado);
        }
    }

    private void escalarAvaliadorNaPauta(final AvaliadorEntity avaliador,final PautaEntity pauta) {
        EscalaEntity escala = new EscalaEntity();
        escala.setAvaliador(avaliador);
        escala.setPauta(pauta);
        escala.setCriadoEm(LocalDateTime.now());
        escalaRepository.save(escala);
    }

    private AvaliadorEntity selecionarAvaliador(final List<AvaliadorEntity> avaliadores) {
        return avaliadores.stream()
                .min(Comparator.comparingInt(AvaliadorEntity::calcularCargaTrabalho))
                .orElseThrow(() -> new RuntimeException("Nenhum avaliador disponivel para escala"));
    }
    
    @Transactional
    public void escalarPautistas(final PautaEntity pauta) {

        List<PautistaEntity> pautistasDisponiveis = pautistaRepository.buscarPautistasDisponiveis(pauta.getData());
        var pautistaSelecionado = selecionarPautista(pautistasDisponiveis, pauta.getOrgaoJulgador());
        escalarPautistaNaPauta(pautistaSelecionado, pauta);
    }
    
    private void escalarPautistaNaPauta(final PautistaEntity pautista, final PautaEntity pauta) {
        EscalaEntity escala = escalaRepository.findByPauta_PautaId(pauta.getPautaId());
        escala.setPautista(pautista);
        escala.setPauta(pauta);
        escala.setCriadoEm(LocalDateTime.now());
        escalaRepository.save(escala);
    }

    private PautistaEntity selecionarPautista(final List<PautistaEntity> pautistas, OrgaoJulgadorEntity orgaoJulgador) {
        Optional<PautistaEntity> pautistaComPreferencia = pautistas.stream()
                .filter(pautista -> pautista.temPreferenciaPorOrgaoJulgador(orgaoJulgador))
                .min(Comparator.comparingInt(PautistaEntity::calcularCargaTrabalho));

        return pautistaComPreferencia.orElseGet(() -> pautistas.stream()
                .min(Comparator.comparingInt(PautistaEntity::calcularCargaTrabalho))
                .orElseThrow(() -> new RuntimeException("Nenhum pautista disponível para escala")));

    }
}
