package br.gov.agu.nutec.msescala.controller;

import br.gov.agu.nutec.msescala.dto.request.EscalaRequestDTO;
import br.gov.agu.nutec.msescala.producer.PautaPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/escalar")
@RequiredArgsConstructor
public class EscalaController {

    private final PautaPublisher pautaPublisher;



    @PostMapping("/avaliadores")
    public ResponseEntity<?> escalarAvaliadores(@RequestBody EscalaRequestDTO escalaRequestDTO,@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        pautaPublisher.iniciarEscalaAvaliadores(escalaRequestDTO, token);
        return ResponseEntity.ok("Avaliadores escalados com sucesso!");
    }

    @PostMapping("/pautistas")
    public ResponseEntity<?> escalarPautistas(@RequestBody EscalaRequestDTO escalaRequestDTO) {
        pautaPublisher.iniciarEscalaPautistas(escalaRequestDTO);
        return ResponseEntity.ok("Pautistas escalados com sucesso!");
    }
}
