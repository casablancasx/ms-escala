package br.gov.agu.nutec.msescala.repository;

import br.gov.agu.nutec.msescala.entity.EscalaEntity;
import br.gov.agu.nutec.msescala.entity.PautistaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscalaRepository extends JpaRepository<EscalaEntity, Long> {

    EscalaEntity findByPauta_PautaId(Long pautaPautaId);


}
