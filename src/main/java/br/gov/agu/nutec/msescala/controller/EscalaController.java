package br.gov.agu.nutec.msescala.controller;

import br.gov.agu.nutec.msescala.dto.request.EscalaRequestDTO;
import br.gov.agu.nutec.msescala.producer.PautaPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/escalar")
@RequiredArgsConstructor
public class EscalaController {

    private final PautaPublisher pautaPublisher;



    @PostMapping("/avaliadores")
    public ResponseEntity<Map<String,String>> escalarAvaliadores(@RequestBody EscalaRequestDTO escalaRequestDTO, @RequestHeader("Authorization") String token) {
        pautaPublisher.iniciarEscalaAvaliadores(escalaRequestDTO, token.replace("Bearer ", ""));
        return ResponseEntity.ok(Map.of("message","Processo de escala de avaliadores iniciado. A conclusão pode levar alguns minutos."));
    }

//    @PostMapping("/pautistas")
//    public ResponseEntity<?> escalarPautistas(@RequestBody EscalaRequestDTO escalaRequestDTO,@RequestHeader("Authorization") String token) {
//        pautaPublisher.iniciarEscalaPautistas(escalaRequestDTO, token.replace("Bearer ", ""));
//        return ResponseEntity.ok("Pautistas escalados com sucesso!");
//    }
}
