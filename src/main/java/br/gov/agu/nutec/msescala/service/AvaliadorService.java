package br.gov.agu.nutec.msescala.service;

import br.gov.agu.nutec.msescala.dto.CadastroAvaliadorRequestDTO;
import br.gov.agu.nutec.msescala.dto.TokenDecoded;
import br.gov.agu.nutec.msescala.entity.AvaliadorEntity;
import br.gov.agu.nutec.msescala.entity.UsuarioEntity;
import br.gov.agu.nutec.msescala.repository.AvaliadorRepository;
import br.gov.agu.nutec.msescala.repository.UsuarioRepository;
import static br.gov.agu.nutec.msescala.util.TokenUtil.decodeToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AvaliadorService {

    private final UsuarioRepository usuarioRepository;
    private final AvaliadorRepository avaliadorRepository;


    public AvaliadorEntity cadastrarAvaliador(CadastroAvaliadorRequestDTO request, String token) {

        TokenDecoded decoded = decodeToken(token);

        UsuarioEntity user = usuarioRepository.findBySapiensId(decoded.sapiensId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        AvaliadorEntity avaliador = mapDtoToEntity(request);

        avaliador.setAdicionadoPor(user);

        return avaliadorRepository.save(avaliador);
    }

    private static AvaliadorEntity mapDtoToEntity(CadastroAvaliadorRequestDTO request) {
        AvaliadorEntity avaliador = new AvaliadorEntity();
        avaliador.setCpf(request.cpf());
        avaliador.setNome(request.nome());
        avaliador.setEscalaAutomatica(request.escalaAutomatica());
        avaliador.setEmail(request.email());
        avaliador.setCriadoEm(LocalDateTime.now());
        avaliador.setSapiensId(request.sapiensId());
        avaliador.setUnidadeId(request.unidadeId());
        avaliador.setSetorId(request.setorId());
        return avaliador;
    }

}
