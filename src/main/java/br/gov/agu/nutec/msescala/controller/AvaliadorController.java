package br.gov.agu.nutec.msescala.controller;

import br.gov.agu.nutec.msescala.dto.request.CadastroAvaliadorRequestDTO;
import br.gov.agu.nutec.msescala.dto.response.AvaliadorResponseDTO;
import br.gov.agu.nutec.msescala.service.AvaliadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avaliadores")
public class AvaliadorController {

    private final AvaliadorService avaliadorService;
    
    @Autowired
    public AvaliadorController(AvaliadorService avaliadorService) {
        this.avaliadorService = avaliadorService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<AvaliadorResponseDTO> cadastrarAvaliador(@RequestBody CadastroAvaliadorRequestDTO request, @RequestHeader("Authorization") String token) {
        var response = avaliadorService.cadastrarAvaliador(request, token);
        return ResponseEntity.ok(response);
    }
}
