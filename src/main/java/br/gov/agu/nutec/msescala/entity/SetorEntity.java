package br.gov.agu.nutec.msescala.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_setores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SetorEntity {

    @Id
    private Long setorId;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "setor_id", referencedColumnName = "setor_id")
    private UnidadeEntity unidade;

}
