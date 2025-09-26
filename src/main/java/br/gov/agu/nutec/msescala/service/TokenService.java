package br.gov.agu.nutec.msescala.service;

import br.gov.agu.nutec.msescala.dto.TokenDecoded;
import br.gov.agu.nutec.msescala.entity.UsuarioEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TokenService {

    public TokenDecoded decodeToken(String token) {

        token = token.replace("Bearer ", "");

        DecodedJWT jwt = JWT.decode(token);


        List<String> roles = jwt.getClaim("roles").asList(String.class);

        Long setorId = null;
        Long unidadeId = null;
        for (String role : roles) {
            if (role.startsWith("ACL_SETOR_")) {
                setorId = Long.parseLong(role.substring("ACL_SETOR_" .length()));
            }
            if (role.startsWith("ACL_UNIDADE_")) {
                unidadeId = Long.parseLong(role.substring("ACL_UNIDADE_" .length()));
            }
        }

        return new TokenDecoded(
                jwt.getClaim("nome").asString(),
                jwt.getClaim("username").asString(),
                jwt.getClaim("email").asString(),
                jwt.getClaim("id").asLong(),
                setorId,
                unidadeId);
    }


}
