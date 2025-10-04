package br.gov.agu.nutec.msescala.service;

import br.gov.agu.nutec.msescala.dto.request.AvaliadorRequestDTO;
import br.gov.agu.nutec.msescala.dto.request.UnidadeRequestDTO;
import br.gov.agu.nutec.msescala.entity.UnidadeEntity;
import br.gov.agu.nutec.msescala.repository.UnidadeRepository;
import org.springframework.stereotype.Service;

@Service
public class UnidadeService {


    private final UnidadeRepository unidadeRepository;

    public UnidadeService(UnidadeRepository unidadeRepository) {
        this.unidadeRepository = unidadeRepository;
    }

    public UnidadeEntity buscarUnidade(AvaliadorRequestDTO request) {
        return unidadeRepository.findById(request.unidade().unidadeId())
                .orElseGet(() -> criarUnidade(request));
    }

    private UnidadeEntity criarUnidade(AvaliadorRequestDTO request) {
        return unidadeRepository.save(new UnidadeEntity(request.unidade().unidadeId(), request.unidade().nome()));
    }
}
