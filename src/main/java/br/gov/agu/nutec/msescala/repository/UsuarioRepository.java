package br.gov.agu.nutec.msescala.repository;

import br.gov.agu.nutec.msescala.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    Optional<UsuarioEntity> findBySapiensId(Long sapiensId);
}
