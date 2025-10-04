package br.gov.agu.nutec.msescala.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AvaliadorRequestDTO(
        @NotBlank(message = "É obrigatório informar o nome do avaliador")
        String nome,
        @NotBlank(message = "É obrigatório informar o email do avaliador")
        String email,
        String telefone,
        boolean disponivel,
        Long sapiensId,
        SetoRequestDTO setor,
        UnidadeRequestDTO unidade
) {


}
