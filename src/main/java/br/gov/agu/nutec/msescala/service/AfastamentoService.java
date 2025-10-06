package br.gov.agu.nutec.msescala.service;

import br.gov.agu.nutec.msescala.dto.TokenDecoded;
import br.gov.agu.nutec.msescala.dto.request.AfastamentoRequestDTO;
import br.gov.agu.nutec.msescala.entity.AfastamentoEntity;
import br.gov.agu.nutec.msescala.entity.AvaliadorEntity;
import br.gov.agu.nutec.msescala.entity.UsuarioEntity;
import br.gov.agu.nutec.msescala.exceptions.ResourceNotFoundException;
import br.gov.agu.nutec.msescala.repository.AfastamentoRepository;
import br.gov.agu.nutec.msescala.repository.AvaliadorRepository;
import br.gov.agu.nutec.msescala.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AfastamentoService {

    private final AfastamentoRepository afastamentoRepository;
    private final AvaliadorRepository avaliadorRepository;
    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;

    @Transactional
    public void cadastrarAfastamento(AfastamentoRequestDTO request, String token) {
        validarPeriodo(request.inicio(), request.fim());

        TokenDecoded decoded = tokenService.decodeToken(token);
        UsuarioEntity usuario = usuarioRepository.findBySapiensId(decoded.sapiensId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        AvaliadorEntity avaliador = avaliadorRepository.findById(request.avaliadorId())
                .orElseThrow(() -> new ResourceNotFoundException("Avaliador não encontrado com ID: " + request.avaliadorId()));

        AfastamentoEntity entity = new AfastamentoEntity();
        entity.setInicio(request.inicio());
        entity.setFim(request.fim());
        entity.setTipoAfastamento(request.tipoAfastamento());
        entity.setAvaliador(avaliador);
        entity.setAdicionadoPor(usuario);
        entity.setObservacao(request.observacao());

        afastamentoRepository.save(entity);
    }

    private void validarPeriodo(LocalDate inicio, LocalDate fim) {
        if (inicio == null || fim == null) {
            throw new IllegalArgumentException("Período inválido: datas obrigatórias");
        }
        if (fim.isBefore(inicio)) {
            throw new IllegalArgumentException("Período inválido: data fim anterior à data início");
        }
    }
}
