package br.gov.agu.nutec.msescala.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UnidadeRequestDTO(
        @NotNull(message = "O ID da unidade não pode ser nulo")
        Long unidadeId,
        @NotBlank(message = "O nome da unidade não pode ser vazio")
        String nome
) {
}
