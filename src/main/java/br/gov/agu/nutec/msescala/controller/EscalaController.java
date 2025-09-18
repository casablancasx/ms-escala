package br.gov.agu.nutec.msescala.controller;

import br.gov.agu.nutec.msescala.dto.EscalaRequestDTO;
import br.gov.agu.nutec.msescala.producer.PautaPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/escalar")
@RequiredArgsConstructor
public class EscalaController {

    private final PautaPublisher pautaPublisher;



    @PostMapping("/avaliadores")
    public ResponseEntity<?> escalarAvaliadores(@RequestBody EscalaRequestDTO escalaRequestDTO) {
        pautaPublisher.iniciarEscalaAvaliadores(escalaRequestDTO);
        return ResponseEntity.ok("Avaliadores escalados com sucesso!");
    }
}
