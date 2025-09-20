package br.gov.agu.nutec.msescala.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AvaliadorResponseDTO(

        @JsonProperty(value = "avaliador_id")
        Long avaliadorId,
        @JsonProperty(value = "nome")
        String nome,
        @JsonProperty(value = "cpf")
        String cpf,
        @JsonProperty(value = "email")
        String email,
        @JsonProperty(value = "setor_id")
        Long setorId,
        @JsonProperty(value = "unidade_id")
        Long unidadeId,
        @JsonProperty(value = "sapiens_id")
        Long sapiensId,
        @JsonProperty(value = "quantidadeAudiencias")
        Long quantidadeAudiencias,
        @JsonProperty(value = "quantidadePautas")
        Long quantidadePautas,
        @JsonProperty(value = "adicionadoPor")
        UsuarioResponseDTO adicionadoPor

) {
}
