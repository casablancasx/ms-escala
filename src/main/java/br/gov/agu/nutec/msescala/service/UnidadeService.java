package br.gov.agu.nutec.msescala.service;

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

    public UnidadeEntity buscarUnidade(UnidadeRequestDTO unidadeRequest) {
        return unidadeRepository.findById(unidadeRequest.unidadeId())
                .orElseGet(() -> criarUnidade(unidadeRequest));
    }

    private UnidadeEntity criarUnidade(UnidadeRequestDTO unidadeRequest) {
        return unidadeRepository.save(new UnidadeEntity(unidadeRequest.unidadeId(), unidadeRequest.nome()));
    }
}
