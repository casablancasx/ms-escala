package br.gov.agu.nutec.msescala.service;

import br.gov.agu.nutec.msescala.dto.TokenDecoded;
import br.gov.agu.nutec.msescala.dto.request.AvaliadorRequestDTO;
import br.gov.agu.nutec.msescala.dto.request.SetoRequestDTO;
import br.gov.agu.nutec.msescala.dto.request.UnidadeRequestDTO;
import br.gov.agu.nutec.msescala.dto.response.AvaliadorResponseDTO;
import br.gov.agu.nutec.msescala.entity.AvaliadorEntity;
import br.gov.agu.nutec.msescala.entity.SetorEntity;
import br.gov.agu.nutec.msescala.entity.UnidadeEntity;
import br.gov.agu.nutec.msescala.entity.UsuarioEntity;
import br.gov.agu.nutec.msescala.exceptions.ResourceNotFoundException;
import br.gov.agu.nutec.msescala.exceptions.UnprocessableEntityException;
import br.gov.agu.nutec.msescala.mapper.AvaliadorMapper;
import br.gov.agu.nutec.msescala.repository.AvaliadorRepository;
import br.gov.agu.nutec.msescala.repository.SetorRepository;
import br.gov.agu.nutec.msescala.repository.UnidadeRepository;
import br.gov.agu.nutec.msescala.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static br.gov.agu.nutec.msescala.util.TokenUtil.decodeToken;

@Service
@RequiredArgsConstructor
public class AvaliadorService {

    private final UsuarioRepository usuarioRepository;
    private final AvaliadorRepository avaliadorRepository;


    private final AvaliadorMapper avaliadorMapper;
    private final UnidadeService unidadeService;
    private final SetorService setorService;


    @Transactional
    public AvaliadorResponseDTO cadastrarAvaliador(AvaliadorRequestDTO request, String token) {
        TokenDecoded decoded = decodeToken(token);

        UsuarioEntity user = usuarioRepository.findBySapiensId(decoded.sapiensId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        UnidadeEntity unidade = unidadeService.buscarUnidade(request.unidade());
        SetorEntity setor = setorService.buscarSetor(request.setor(), unidade);

        AvaliadorEntity avaliador = avaliadorMapper.mapToEntity(request);
        avaliador.setAdicionadoPor(user);
        avaliador.setUnidade(unidade);
        avaliador.setSetor(setor);
        avaliadorRepository.save(avaliador);
        return avaliadorMapper.mapToResponseDTO(avaliador);
    }
    
}
