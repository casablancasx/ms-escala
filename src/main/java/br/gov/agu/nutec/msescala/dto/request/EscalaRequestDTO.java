package br.gov.agu.nutec.msescala.dto.request;

import br.gov.agu.nutec.msescala.enums.TipoContestacao;
import br.gov.agu.nutec.msescala.enums.Uf;

import java.time.LocalDate;
import java.util.List;

public record EscalaRequestDTO(
        Integer setorOrigemId,
        Integer especieTarefaId,
        LocalDate dataInicio,
        LocalDate dataFim,
        List<Uf> ufs,
        List<TipoContestacao> tipoContestacao,
        List<Long> orgaoJulgadorIds,
        List<Long> avaliadorIds,
        List<Long> pautistaIds
) {
}
