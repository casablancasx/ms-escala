package br.gov.agu.nutec.msescala.repository;

import br.gov.agu.nutec.msescala.entity.PautistaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PautistaRepository extends JpaRepository<PautistaEntity, Integer> {


        @Query(value = """
            SELECT p FROM PautistaEntity p
            WHERE p.disponivel = true
            AND p.pautistaId NOT IN (
                SELECT e.pautista.pautistaId FROM EscalaEntity e
                JOIN e.pauta pa
                WHERE pa.data = :data
            )
            AND NOT EXISTS (
                SELECT 1 FROM AfastamentoEntity a
                WHERE a.pautista = p
                AND :data BETWEEN a.inicio AND a.fim
            )
            """)
    List<PautistaEntity> buscarPautistasDisponiveis(@Param("data") LocalDate data);
}
