package br.gov.agu.nutec.msescala.dto.request;

import br.gov.agu.nutec.msescala.enums.Uf;

import java.time.LocalDate;

public record EscalaRequestDTO(
        Integer setorOrigemId,
        Integer especieTarefaId,
        LocalDate dataInicio,
        LocalDate dataFim,
        Uf uf
) {
}
