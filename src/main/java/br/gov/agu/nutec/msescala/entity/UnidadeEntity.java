package br.gov.agu.nutec.msescala.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "tb_unidades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UnidadeEntity {


    @Id
    private Long unidadeId;

    private String nome;

    @OneToMany(mappedBy = "unidade", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SetorEntity> setores = Set.of();


    public UnidadeEntity(Long unidadeId, String nome) {
        this.unidadeId = unidadeId;
        this.nome = nome;
    }
}
