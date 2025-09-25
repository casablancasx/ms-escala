package br.gov.agu.nutec.msescala.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_avaliadores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvaliadorEntity extends EntidadeSapiens{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer avaliadorId;

    private String telefone;

    private Integer quantidadePautas = 0;

    private Integer quantidadeAudiencias = 0;

    private boolean disponivel = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adicionado_por_id")
    private UsuarioEntity adicionadoPor;

    @OneToMany(mappedBy = "avaliador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AfastamentoEntity> afastamentos = new ArrayList<>();

    @OneToMany(mappedBy = "avaliador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EscalaEntity> escalas = new ArrayList<>();



    public int calcularCargaTrabalho() {
        final int PESO_PAUTA = 1;
        final int PESO_AUDIENCIA = 2;
        return (quantidadePautas * PESO_PAUTA) + (quantidadeAudiencias * PESO_AUDIENCIA);
    }

    public void incrementarPautas() {
        this.quantidadePautas++;
    }

    public void incrementarAudiencias(PautaEntity pauta) {
        quantidadeAudiencias += pauta.getAudiencias().size();
    }
}
