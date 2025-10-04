package br.gov.agu.nutec.msescala.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AvaliadorResponseDTO(

        @JsonProperty(value = "avaliador_id")
        Long avaliadorId,
        @JsonProperty(value = "nome")
        String nome,
        @JsonProperty(value = "telefone")
        String telefone,
        @JsonProperty(value = "email")
        String email,
        @JsonProperty(value = "setor")
        String setor,
        @JsonProperty(value = "unidade")
        String unidade,
        @JsonProperty(value = "sapiens_id")
        Long sapiensId,
        @JsonProperty(value = "quantidadeAudiencias")
        Integer quantidadeAudiencias,
        @JsonProperty(value = "quantidadePautas")
        Integer quantidadePautas,
        @JsonProperty(value = "score")
        Integer score,
        @JsonProperty(value = "disponivel")
        boolean disponivel,
        @JsonProperty(value = "adicionadoPor")
        String adicionadoPor
) {
}
