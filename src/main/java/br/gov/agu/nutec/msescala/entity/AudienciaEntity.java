package br.gov.agu.nutec.msescala.entity;

import br.gov.agu.nutec.msescala.config.StatusAnaliseComparecimentoConverter;
import br.gov.agu.nutec.msescala.enums.StatusAnaliseComparecimento;
import br.gov.agu.nutec.msescala.enums.StatusCadastroTarefa;
import br.gov.agu.nutec.msescala.enums.TipoContestacao;
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

    private String nomeParte;

    @ManyToOne
    @JoinColumn(name = "pauta_id")
    private PautaEntity pauta;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_cadastro_tarefa_avaliador")
    private StatusCadastroTarefa statusCadastroTarefaAvaliador;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_cadastro_tarefa_pautista")
    private StatusCadastroTarefa statusCadastroTarefaPautista;

    @Convert(converter = StatusAnaliseComparecimentoConverter.class)
    private StatusAnaliseComparecimento analiseComparecimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_contestacao")
    private TipoContestacao tipoContestacao;
}
