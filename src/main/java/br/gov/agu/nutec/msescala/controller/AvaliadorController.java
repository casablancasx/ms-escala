package br.gov.agu.nutec.msescala.controller;

import br.gov.agu.nutec.msescala.dto.request.AvaliadorRequestDTO;
import br.gov.agu.nutec.msescala.dto.response.AvaliadorResponseDTO;
import br.gov.agu.nutec.msescala.dto.response.PageResponse;
import br.gov.agu.nutec.msescala.service.AvaliadorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avaliador")
public class AvaliadorController {

    private final AvaliadorService avaliadorService;
    
    @Autowired
    public AvaliadorController(AvaliadorService avaliadorService) {
        this.avaliadorService = avaliadorService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<AvaliadorResponseDTO> cadastrarAvaliador(@RequestBody @Valid AvaliadorRequestDTO request, @RequestHeader("Authorization") String token) {
        var response = avaliadorService.cadastrarAvaliador(request, token);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PageResponse<AvaliadorResponseDTO>> listarAvaliadores(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "sort", defaultValue = "nome") String sort,
            @RequestHeader("Authorization") String token) {

        var response = avaliadorService.listarAvaliadores(page, size, nome, sort, token);

        return ResponseEntity.ok(response);
    }



}
