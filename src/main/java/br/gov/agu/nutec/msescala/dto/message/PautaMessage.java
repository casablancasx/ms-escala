package br.gov.agu.nutec.msescala.dto.message;


import java.util.List;

public record PautaMessage(
        String context,
        Long pautaId,
        Integer setorOrigemId,
        Integer especieTarefaId,
        List<Integer> avaliadoresIds,
        List<Integer> pautistasIds,
        String token
) {
}

