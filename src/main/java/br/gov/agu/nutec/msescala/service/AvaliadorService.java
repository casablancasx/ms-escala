package br.gov.agu.nutec.msescala.service;

import br.gov.agu.nutec.msescala.dto.TokenDecoded;
import br.gov.agu.nutec.msescala.dto.request.AvaliadorRequestDTO;
import br.gov.agu.nutec.msescala.dto.response.AvaliadorResponseDTO;
import br.gov.agu.nutec.msescala.dto.response.PageResponse;
import br.gov.agu.nutec.msescala.entity.AvaliadorEntity;
import br.gov.agu.nutec.msescala.entity.SetorEntity;
import br.gov.agu.nutec.msescala.entity.UnidadeEntity;
import br.gov.agu.nutec.msescala.entity.UsuarioEntity;
import br.gov.agu.nutec.msescala.exceptions.ResourceNotFoundException;
import br.gov.agu.nutec.msescala.mapper.AvaliadorMapper;
import br.gov.agu.nutec.msescala.repository.AvaliadorRepository;
import br.gov.agu.nutec.msescala.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AvaliadorService {

    private final UsuarioRepository usuarioRepository;
    private final AvaliadorRepository avaliadorRepository;


    private final AvaliadorMapper avaliadorMapper;
    private final SetorService setorService;
    private final TokenService tokenService;


    @Transactional
    public AvaliadorResponseDTO cadastrarAvaliador(AvaliadorRequestDTO request, String token) {
        TokenDecoded decoded = tokenService.decodeToken(token);

        UsuarioEntity user = usuarioRepository.findBySapiensId(decoded.sapiensId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        SetorEntity setor = setorService.buscarSetor(request);

        AvaliadorEntity avaliador = avaliadorMapper.mapToEntity(request);
        avaliador.setSapiensId(request.sapiensId());
        avaliador.setAdicionadoPor(user);
        avaliador.setSetor(setor);
        avaliadorRepository.save(avaliador);
        return avaliadorMapper.mapToResponseDTO(avaliador);
    }

    public PageResponse<AvaliadorResponseDTO> listarAvaliadores(int page, int size, String nome, String sort, String token) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        Page<AvaliadorEntity> avaliadoresPage;

        if (nome == null || nome.isEmpty()) {
            avaliadoresPage = avaliadorRepository.findAll(pageable);
        } else {
            avaliadoresPage = avaliadorRepository.findByNomeContainingIgnoreCase(nome, pageable);
        }

        var avaliadoresDTO = avaliadoresPage.getContent().stream()
                .map(avaliadorMapper::mapToResponseDTO)
                .toList();

        return new PageResponse<>(
                avaliadoresDTO,
                avaliadoresPage.getNumber(),
                avaliadoresPage.getSize(),
                avaliadoresPage.getTotalElements(),
                avaliadoresPage.getTotalPages()
        );
    }

    public AvaliadorResponseDTO atualizarAvaliador(Integer id, @Valid AvaliadorRequestDTO request, String token) {
        AvaliadorEntity avaliadorExistente = avaliadorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliador não encontrado com ID: " + id));

        avaliadorExistente.setNome(request.nome());
        avaliadorExistente.setEmail(request.email());
        avaliadorExistente.setTelefone(request.telefone());
        avaliadorExistente.setDisponivel(request.disponivel());
        avaliadorRepository.save(avaliadorExistente);

        return avaliadorMapper.mapToResponseDTO(avaliadorExistente);
    }

    public void deletarAvaliador(Integer id) {
        avaliadorRepository.deleteById(id);
    }
}
