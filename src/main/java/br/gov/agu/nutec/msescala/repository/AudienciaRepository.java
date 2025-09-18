package br.gov.agu.nutec.msescala.repository;

import br.gov.agu.nutec.msescala.entity.AudienciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudienciaRepository extends JpaRepository<AudienciaEntity, Long> {
}