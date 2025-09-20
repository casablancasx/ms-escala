package br.gov.agu.nutec.msescala.entity;

import br.gov.agu.nutec.msescala.enums.StatusCadastro;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class EntidadeSapiens {

    private Long sapiensId;

    private String nome;

    private String cpf;

    private String email;

    private Long setorId;

    private Long unidadeId;

    private LocalDateTime criadoEm;

}
