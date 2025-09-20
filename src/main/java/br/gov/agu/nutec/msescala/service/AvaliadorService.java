package br.gov.agu.nutec.msescala.service;

import br.gov.agu.nutec.msescala.dto.TokenDecoded;
import br.gov.agu.nutec.msescala.dto.request.CadastroAvaliadorRequestDTO;
import br.gov.agu.nutec.msescala.dto.response.AvaliadorResponseDTO;
import br.gov.agu.nutec.msescala.entity.AvaliadorEntity;
import br.gov.agu.nutec.msescala.entity.UsuarioEntity;
import br.gov.agu.nutec.msescala.mapper.AvaliadorMapper;
import br.gov.agu.nutec.msescala.repository.AvaliadorRepository;
import br.gov.agu.nutec.msescala.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.gov.agu.nutec.msescala.util.TokenUtil.decodeToken;

@Service
public class AvaliadorService {

    private final UsuarioRepository usuarioRepository;
    private final AvaliadorRepository avaliadorRepository;
    private final AvaliadorMapper avaliadorMapper;
    
    @Autowired
    public AvaliadorService(UsuarioRepository usuarioRepository, 
                           AvaliadorRepository avaliadorRepository, 
                           AvaliadorMapper avaliadorMapper) {
        this.usuarioRepository = usuarioRepository;
        this.avaliadorRepository = avaliadorRepository;
        this.avaliadorMapper = avaliadorMapper;
    }

    @Transactional
    public AvaliadorResponseDTO cadastrarAvaliador(CadastroAvaliadorRequestDTO request, String token) {
        TokenDecoded decoded = decodeToken(token);

        UsuarioEntity user = usuarioRepository.findBySapiensId(decoded.sapiensId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        AvaliadorEntity avaliador = avaliadorMapper.mapToEntity(request);
        avaliador.setAdicionadoPor(user);

        return avaliadorMapper.mapToResponseDTO(avaliadorRepository.save(avaliador));
    }



}
