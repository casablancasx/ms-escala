package br.gov.agu.nutec.msescala.controller;

import br.gov.agu.nutec.msescala.dto.EscalaRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/escalar")
public class EscalaController {



    @PostMapping("/avaliadores")
    public ResponseEntity<?> escalarAvaliadores(@RequestBody EscalaRequestDTO escalaRequestDTO) {
        // Lógica para escalar avaliadores
        return ResponseEntity.ok("Avaliadores escalados com sucesso!");
    }
}
