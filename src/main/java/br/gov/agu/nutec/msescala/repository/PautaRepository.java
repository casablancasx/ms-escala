package br.gov.agu.nutec.msescala.repository;

import br.gov.agu.nutec.msescala.entity.PautaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PautaRepository extends JpaRepository<PautaEntity, Long> {

     @Query("SELECT p FROM PautaEntity p WHERE " +
           "p.data BETWEEN :dataInicio AND :dataFim AND " +
           "p.orgaoJulgador.uf.sigla = :uf AND " +
           "NOT EXISTS (SELECT e FROM EscalaEntity e WHERE e.pauta = p AND e.avaliador IS NOT NULL)")
     List<PautaEntity> buscarPautasSemAvaliadoresEscalados(
        @Param("dataInicio") LocalDate dataInicio,
        @Param("dataFim") LocalDate dataFim,
        @Param("uf") String uf);
}