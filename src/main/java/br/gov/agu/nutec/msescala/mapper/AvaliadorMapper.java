package br.gov.agu.nutec.msescala.mapper;

import br.gov.agu.nutec.msescala.dto.request.CadastroAvaliadorRequestDTO;
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
    @Mapping(target = "afastado", constant = "false")
    @Mapping(target = "escalaAutomatica", source = "escalaAutomatica")
    @Mapping(target = "quantidadeAudiencias", constant = "0")
    @Mapping(target = "quantidadePautas", constant = "0")
    @Mapping(target = "criadoEm", expression = "java(java.time.LocalDateTime.now())")
    AvaliadorEntity mapToEntity(CadastroAvaliadorRequestDTO requestDTO);


    @Mapping(target = "adicionadoPor", source = "adicionadoPor", qualifiedByName = "mapUsuarioToDto")
    AvaliadorResponseDTO mapToResponseDTO(AvaliadorEntity entity);

    /**
     * Maps the Usuario entity to DTO while preventing circular references
     */
    @Named("mapUsuarioToDto")
    default UsuarioResponseDTO mapUsuarioToDto(UsuarioEntity usuario) {
        if (usuario == null) {
            return null;
        }

        return new UsuarioResponseDTO(
                usuario.getUsuarioId().longValue(),
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getEmail(),
                usuario.getSetorId(),
                usuario.getUnidadeId(),
                usuario.getSapiensId()
        );
    }
}
