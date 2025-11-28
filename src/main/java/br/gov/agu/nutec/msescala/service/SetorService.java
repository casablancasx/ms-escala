package br.gov.agu.nutec.msescala.service;

import br.gov.agu.nutec.msescala.dto.request.AvaliadorRequestDTO;
import br.gov.agu.nutec.msescala.dto.request.SetoRequestDTO;
import br.gov.agu.nutec.msescala.entity.SetorEntity;
import br.gov.agu.nutec.msescala.entity.UnidadeEntity;
import br.gov.agu.nutec.msescala.repository.SetorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SetorService {


    private final SetorRepository setorRepository;
    private final UnidadeService unidadeService;



    public SetorEntity buscarSetor(AvaliadorRequestDTO request) {
        return setorRepository.findById(request.setor().setorId()).orElseGet(
                () -> criarSetor(request)
        );
    }


    private SetorEntity criarSetor(AvaliadorRequestDTO request) {
        var unidade = unidadeService.buscarUnidade(request);
        return setorRepository.save(new SetorEntity(request.setor().setorId(), request.setor().nome(), unidade));
    }
}
