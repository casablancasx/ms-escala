package br.gov.agu.nutec.msescala.dto;

public record CadastroAvaliadorRequestDTO(
        String nome,
        String email,
        String cpf,
        Long sapiensId,
        Long unidadeId,
        Long setorId,
        boolean escalaAutomatica
) {


}
