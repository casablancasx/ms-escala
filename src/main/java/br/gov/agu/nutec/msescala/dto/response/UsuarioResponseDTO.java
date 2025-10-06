package br.gov.agu.nutec.msescala.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UsuarioResponseDTO(
        Long sapiensId,
        String nome,
        String cpf,
        String email,
        Long setorId,
        Long unidadeId
) {

}
