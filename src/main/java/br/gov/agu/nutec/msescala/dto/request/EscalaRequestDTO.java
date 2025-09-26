package br.gov.agu.nutec.msescala.dto.request;

import br.gov.agu.nutec.msescala.enums.Uf;

import java.time.LocalDate;
import java.util.List;

public record EscalaRequestDTO(
        Integer setorOrigemId,
        Integer especieTarefaId,
        LocalDate dataInicio,
        LocalDate dataFim,
        Uf uf,
        List<Long> orgaoJulgadorIds,
        List<Integer> avaliadorIds,
        List<Integer> pautistaIds
) {
}
