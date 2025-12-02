package br.gov.agu.nutec.msescala.config;

import br.gov.agu.nutec.msescala.enums.StatusAnaliseComparecimento;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusAnaliseComparecimentoConverter implements AttributeConverter<StatusAnaliseComparecimento, String> {

    @Override
    public String convertToDatabaseColumn(StatusAnaliseComparecimento attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getDescricao();
    }

    @Override
    public StatusAnaliseComparecimento convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        return StatusAnaliseComparecimento.fromDescricao(dbData);
    }
}
