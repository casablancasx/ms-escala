package br.gov.agu.nutec.msescala.service;

import br.gov.agu.nutec.msescala.dto.message.PautaMessage;
import br.gov.agu.nutec.msescala.entity.*;
import br.gov.agu.nutec.msescala.exceptions.SemPautistaDisponivelException;
import br.gov.agu.nutec.msescala.repository.AvaliadorRepository;
import br.gov.agu.nutec.msescala.repository.EscalaRepository;
import br.gov.agu.nutec.msescala.repository.PautaRepository;
import br.gov.agu.nutec.msescala.repository.PautistaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static br.gov.agu.nutec.msescala.enums.StatusEscalaPauta.ESCALADA;
import static br.gov.agu.nutec.msescala.enums.StatusEscalaPauta.SEM_PAUTISTA_DISPONIVEL;


@Service
@RequiredArgsConstructor
public class EscalaService {

    private final AvaliadorRepository avaliadorRepository;
    private final EscalaRepository escalaRepository;
    private final PautaRepository pautaRepository;
    private final PautistaRepository pautistaRepository;

    private final CadastrarTarefaService cadastrarTarefaService;


    @Transactional
    public void escalarAvaliadores(final PautaMessage pautaMessage) {


        PautaEntity pauta = buscarPautaPorId(pautaMessage.pautaId());

        List<AvaliadorEntity> avaliadores = new ArrayList<>();

        if (pautaMessage.avaliadoresIds().isEmpty()) {
            avaliadores = avaliadorRepository.buscarAvaliadoresDisponveis(pauta.getData());
        } else {
            avaliadores = avaliadorRepository.findAllById(pautaMessage.avaliadoresIds());
        }

        var avaliadorSelecionado = selecionarAvaliador(avaliadores);
        escalarAvaliadorNaPauta(avaliadorSelecionado, pauta);


        for (AudienciaEntity audiencia : pauta.getAudiencias()) {
            cadastrarTarefaService.cadastrarTarefaSapiens(audiencia, pauta, avaliadorSelecionado, pautaMessage);
        }
    }

    private PautaEntity buscarPautaPorId(Long pautaId) {
        return pautaRepository.findById(pautaId)
                .orElseThrow(() -> new RuntimeException("Pauta não encontrada com ID: " + pautaId));
    }

    private void escalarAvaliadorNaPauta(final AvaliadorEntity avaliador, final PautaEntity pauta) {
        EscalaEntity escala = escalaRepository.findByPauta_PautaId(pauta.getPautaId());
        if (escala == null) {
            escala = new EscalaEntity();
            escala.setPauta(pauta);
        }
        escala.setAvaliador(avaliador);
        escala.setCriadoEm(LocalDateTime.now());
        escalaRepository.save(escala);

        avaliador.incrementarPautas();
        avaliador.incrementarAudiencias(pauta);
        avaliadorRepository.save(avaliador);

        pauta.setStatusEscalaAvaliador(ESCALADA);
        pautaRepository.save(pauta);
    }

    private AvaliadorEntity selecionarAvaliador(final List<AvaliadorEntity> avaliadores) {
        return avaliadores.stream()
                .min(Comparator.comparingInt(AvaliadorEntity::calcularCargaTrabalho))
                .orElseThrow(() -> new RuntimeException("Nenhum avaliador disponivel para escala"));
    }

    @Transactional
    public void escalarPautistas(final PautaMessage pautaMessage) {

        PautaEntity pauta = buscarPautaPorId(pautaMessage.pautaId());

        List<PautistaEntity> pautistasDispinveis = new ArrayList<>();

        if (pautaMessage.pautistasIds().isEmpty()) {
            pautistasDispinveis = pautistaRepository.buscarPautistasDisponiveis(pauta.getData());
        } else {
            pautistasDispinveis = pautistaRepository.findAllById(pautaMessage.pautistasIds());
        }


        try {
            var pautistaSelecionado = selecionarPautista(pautistasDispinveis, pauta.getOrgaoJulgador());
            escalarPautistaNaPauta(pautistaSelecionado, pauta);
            for (AudienciaEntity audiencia : pauta.getAudiencias()) {
                cadastrarTarefaService.cadastrarTarefaSapiens(audiencia, pauta, pautistaSelecionado, pautaMessage);
            }

        } catch (SemPautistaDisponivelException e) {
            pauta.setStatusEscalaPautista(SEM_PAUTISTA_DISPONIVEL);
            pautaRepository.save(pauta);
        }


    }

    private void escalarPautistaNaPauta(final PautistaEntity pautista, final PautaEntity pauta) {
        EscalaEntity escala = escalaRepository.findByPauta_PautaId(pauta.getPautaId());
        if (escala == null) {
            escala = new EscalaEntity();
            escala.setPauta(pauta);
        }
        escala.setPautista(pautista);
        escala.setCriadoEm(LocalDateTime.now());
        escalaRepository.save(escala);

        pautista.incrementarPautas();
        pautista.incrementarAudiencias(pauta);
        pautistaRepository.save(pautista);

        pauta.setStatusEscalaPautista(ESCALADA);
        pautaRepository.save(pauta);
    }

    private PautistaEntity selecionarPautista(final List<PautistaEntity> pautistas, OrgaoJulgadorEntity orgaoJulgador) {
        Optional<PautistaEntity> pautistaComPreferencia = pautistas.stream()
                .filter(pautista -> pautista.temPreferenciaPorOrgaoJulgador(orgaoJulgador))
                .min(Comparator.comparingInt(PautistaEntity::calcularCargaTrabalho));

        return pautistaComPreferencia.orElseGet(() -> pautistas.stream()
                .min(Comparator.comparingInt(PautistaEntity::calcularCargaTrabalho))
                .orElseThrow(() -> new SemPautistaDisponivelException("Nenhum pautista disponível para escala")));

    }
}
