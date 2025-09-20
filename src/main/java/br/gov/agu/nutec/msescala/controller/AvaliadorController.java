package br.gov.agu.nutec.msescala.controller;

import br.gov.agu.nutec.msescala.dto.CadastroAvaliadorRequestDTO;
import br.gov.agu.nutec.msescala.entity.AvaliadorEntity;
import br.gov.agu.nutec.msescala.service.AvaliadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avaliadores")
@RequiredArgsConstructor
public class AvaliadorController {

    private final AvaliadorService avaliadorService;


    @PostMapping("/cadastrar")
    public ResponseEntity<AvaliadorEntity> cadastrarAvaliador(@RequestBody  CadastroAvaliadorRequestDTO request, @RequestHeader("Authorization") String token) {
        var novoAvaliador = avaliadorService.cadastrarAvaliador(request,token);
        return ResponseEntity.ok(novoAvaliador);


    }
}
