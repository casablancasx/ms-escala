package br.gov.agu.nutec.msescala.repository;

import br.gov.agu.nutec.msescala.entity.PautaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepository extends JpaRepository<PautaEntity, Long> {


}
