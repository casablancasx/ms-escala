package br.gov.agu.nutec.msescala.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusAnaliseComparecimento {
    COMPARECER("Comparecer"),
    NAO_COMPARECER("Não Comparecer"),
    PENDENTE("Pendente"),
    ANALISE_PENDENTE("Análise Pendente");

    private final String descricao;

    StatusAnaliseComparecimento(String descricao) {
        this.descricao = descricao;
    }

    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static StatusAnaliseComparecimento fromDescricao(String descricao) {
        for (StatusAnaliseComparecimento status : values()) {
            if (status.descricao.equalsIgnoreCase(descricao) || status.name().equalsIgnoreCase(descricao)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status de análise de comparecimento desconhecido: " + descricao);
    }
}
