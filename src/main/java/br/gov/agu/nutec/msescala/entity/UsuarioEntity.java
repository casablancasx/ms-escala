package br.gov.agu.nutec.msescala.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tb_usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity extends EntidadeSapiens {

    private String cpf;

    @OneToMany(mappedBy = "adicionadoPor")
    private List<PautistaEntity> pautistasAdicionados;

    @OneToMany(mappedBy = "adicionadoPor")
    private List<AvaliadorEntity> avaliadoresAdicionados;
}
