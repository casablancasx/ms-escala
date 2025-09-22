package br.gov.agu.nutec.msescala.dto.message;



public record PautaMessage(
        String context,
        Long pautaId,
        Integer setorOrigemId,
        Integer especieTarefaId,
        String token
) {
}

