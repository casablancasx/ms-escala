package br.gov.agu.nutec.msescala.dto.request;

import br.gov.agu.nutec.msescala.enums.TipoAfastamento;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AfastamentoRequestDTO(
        @NotNull(message = "É obrigatório informar o avaliador")
        Long avaliadorId,
        @NotNull(message = "É obrigatório informar a data de início")
        @FutureOrPresent(message = "A data de início não pode estar no passado")
        LocalDate inicio,
        @NotNull(message = "É obrigatório informar a data de fim")
        LocalDate fim,
        @NotNull(message = "É obrigatório informar o tipo de afastamento")
        TipoAfastamento tipoAfastamento,
        String observacao
) {
}
