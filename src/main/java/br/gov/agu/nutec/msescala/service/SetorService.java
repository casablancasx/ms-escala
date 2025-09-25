package br.gov.agu.nutec.msescala.service;

import br.gov.agu.nutec.msescala.dto.request.SetoRequestDTO;
import br.gov.agu.nutec.msescala.entity.SetorEntity;
import br.gov.agu.nutec.msescala.entity.UnidadeEntity;
import br.gov.agu.nutec.msescala.repository.SetorRepository;
import org.springframework.stereotype.Service;

@Service
public class SetorService {


    private final SetorRepository setorRepository;

    public SetorService(SetorRepository setorRepository) {
        this.setorRepository = setorRepository;
    }

    public SetorEntity buscarSetor(SetoRequestDTO setoRequest, UnidadeEntity unidade) {
        return setorRepository.findById(setoRequest.setorId()).orElseGet(
                () -> criarSetor(setoRequest, unidade)
        );
    }


    private SetorEntity criarSetor(SetoRequestDTO setoRequest, UnidadeEntity unidade) {
        return new SetorEntity(setoRequest.setorId(), setoRequest.nome(), unidade);
    }
}
