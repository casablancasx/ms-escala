package br.gov.agu.nutec.msescala.repository;

import br.gov.agu.nutec.msescala.entity.PautistaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PautistaRepository extends JpaRepository<PautistaEntity, Long> {
}
