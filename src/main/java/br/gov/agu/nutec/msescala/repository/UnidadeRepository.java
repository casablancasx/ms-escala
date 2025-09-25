package br.gov.agu.nutec.msescala.repository;

import br.gov.agu.nutec.msescala.entity.UnidadeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadeRepository extends JpaRepository<UnidadeEntity, Long> {
}
