package br.gov.agu.nutec.msescala.entity;

import jakarta.persistence.*;
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

    @Id
    @Column(name = "sapiens_id")
    private Long sapiensId;

    private String nome;

    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "setor_id")
    private SetorEntity setor;

    private LocalDateTime criadoEm;

    public Long getSetorId() {
        return setor.getSetorId();
    }

}
