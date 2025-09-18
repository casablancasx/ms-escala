package br.gov.agu.nutec.msescala.entity;


import br.gov.agu.nutec.msescala.enums.StatusAnaliseComparecimento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tb_pautas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PautaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pautaId;

    private LocalDate data;

    private String turno;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_analise_comparecimento")
    private StatusAnaliseComparecimento statusAnaliseComparecimento;

    @ManyToOne
    @JoinColumn(name = "orgao_julgador_id")
    private OrgaoJulgadorEntity orgaoJulgador;


    @OneToMany(mappedBy = "pauta")
    private List<AudienciaEntity> audiencias;

}
