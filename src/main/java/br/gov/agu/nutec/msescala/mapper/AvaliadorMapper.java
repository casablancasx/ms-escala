package br.gov.agu.nutec.msescala.mapper;

import br.gov.agu.nutec.msescala.dto.request.AvaliadorRequestDTO;
import br.gov.agu.nutec.msescala.dto.response.AvaliadorResponseDTO;
import br.gov.agu.nutec.msescala.dto.response.UsuarioResponseDTO;
import br.gov.agu.nutec.msescala.entity.AvaliadorEntity;
import br.gov.agu.nutec.msescala.entity.UsuarioEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AvaliadorMapper {


    @Mapping(target = "afastamentos", ignore = true)
    @Mapping(target = "escalas", ignore = true)
    @Mapping(target = "setor", ignore = true)
    @Mapping(target = "sapiensId", source = "sapiensId")
    @Mapping(target = "quantidadeAudiencias", constant = "0")
    @Mapping(target = "quantidadePautas", constant = "0")
    @Mapping(target = "criadoEm", expression = "java(java.time.LocalDateTime.now())")
    AvaliadorEntity mapToEntity(AvaliadorRequestDTO requestDTO);


    @Mapping(target = "adicionadoPor", source = "adicionadoPor.nome")
    @Mapping(target = "setor", source = "setor.nome")
    @Mapping(target = "unidade", source = "setor.unidade.nome")
    @Mapping(target = "score", expression = "java(entity.calcularCargaTrabalho())")
    AvaliadorResponseDTO mapToResponseDTO(AvaliadorEntity entity);

}
