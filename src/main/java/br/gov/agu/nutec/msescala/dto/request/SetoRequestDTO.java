package br.gov.agu.nutec.msescala.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SetoRequestDTO(
        @NotNull(message = "O campo setorId é obrigatório")
        Long setorId, // NÃO PODE SER ALTERADO PELO USUARIO
        @NotBlank(message = "O campo nome é obrigatório")
        String nome  // NÃO PODE SER ALTERADO PELO USUARIO
) {
}
