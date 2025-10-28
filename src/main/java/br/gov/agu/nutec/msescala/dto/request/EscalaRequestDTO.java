package br.gov.agu.nutec.msescala.dto.request;

import java.time.LocalDate;
import java.util.List;

public record EscalaRequestDTO(
        Integer setorOrigemId,
        Integer especieTarefaId,
        LocalDate dataInicio,
        LocalDate dataFim,
        List<Integer> ufIds,
        List<Long> orgaoJulgadorIds,
        List<Long> avaliadorIds,
        List<Long> pautistaIds
) {
}
