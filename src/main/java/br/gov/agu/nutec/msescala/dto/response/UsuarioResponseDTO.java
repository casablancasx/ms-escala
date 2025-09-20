package br.gov.agu.nutec.msescala.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UsuarioResponseDTO(
        @JsonProperty("usuario_id")
        Long usuarioId,
        @JsonProperty("nome")
        String nome,
        @JsonProperty("cpf")
        String cpf,
        @JsonProperty("email")
        String email,
        @JsonProperty("setor_id")
        Long setorId,
        @JsonProperty("unidade_id")
        Long unidadeId,
        @JsonProperty("sapiens_id")
        Long sapiensId
) {

}
