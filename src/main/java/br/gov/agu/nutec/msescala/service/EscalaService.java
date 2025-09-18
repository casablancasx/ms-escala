package br.gov.agu.nutec.msescala.service;

import br.gov.agu.nutec.msescala.entity.AvaliadorEntity;
import br.gov.agu.nutec.msescala.entity.EscalaEntity;
import br.gov.agu.nutec.msescala.entity.PautaEntity;
import br.gov.agu.nutec.msescala.repository.AvaliadorRepository;
import br.gov.agu.nutec.msescala.repository.EscalaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;


@Service
@RequiredArgsConstructor
public class EscalaService {

    private final AvaliadorRepository avaliadorRepository;
    private final EscalaRepository escalaRepository;


    @Transactional
    public void escalarAvaliadores(final PautaEntity pauta) {

        List<AvaliadorEntity> avaliadores = avaliadorRepository.buscarAvaliadoresDisponveis(pauta.getData());
        var avaliadorSelecionado = selecionarAvaliador(avaliadores);

        escalarAvaliadorNaPauta(avaliadorSelecionado, pauta);
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

}
