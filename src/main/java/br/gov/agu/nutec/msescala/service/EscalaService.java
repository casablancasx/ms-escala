package br.gov.agu.nutec.msescala.service;

import br.gov.agu.nutec.msescala.dto.EscalaRequestDTO;
import br.gov.agu.nutec.msescala.enums.Uf;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EscalaService {



    public Object escalarAvaliadores(EscalaRequestDTO request) {

        Uf uf = Uf.valueOf(request.uf());






    }





}
