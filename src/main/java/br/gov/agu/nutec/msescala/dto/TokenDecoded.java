package br.gov.agu.nutec.msescala.dto;

public record TokenDecoded(

        String nome,
        String cpf,
        String email,
        Long sapiensId,
        Long unidadeId,
        Long setorId
) {
}

