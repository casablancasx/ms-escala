package br.gov.agu.nutec.msescala.repository;

import br.gov.agu.nutec.msescala.entity.AfastamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AfastamentoRepository extends JpaRepository<AfastamentoEntity, Long> {
}
