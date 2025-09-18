package br.gov.agu.nutec.msescala.entity;

import br.gov.agu.nutec.msescala.enums.StatusCadastro;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_audiencias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AudienciaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long audienciaId;

    private String numeroProcesso;

    private String classeJudicial;

    private String assunto;

    private String nomeParte;

    private String prioridade;

    @ManyToOne
    @JoinColumn(name = "pauta_id")
    private PautaEntity pauta;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_cadastro_avaliador")
    private StatusCadastro statusCadastroAvaliador;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_cadastro_pautista")
    private StatusCadastro statusCadastroPautista;
}
