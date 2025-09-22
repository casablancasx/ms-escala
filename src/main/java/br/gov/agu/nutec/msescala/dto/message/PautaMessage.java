package br.gov.agu.nutec.msescala.dto.message;




public record PautaMessage(
    String context,
    Long pautaId,
    String token
){}

