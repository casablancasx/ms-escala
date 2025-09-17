package br.gov.agu.nutec.msescala.dto;

import java.time.LocalDate;

public record EscalaRequestDTO(
        LocalDate dataIncio,
        LocalDate dataFim,
        String uf
) {
}
