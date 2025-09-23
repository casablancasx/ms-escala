package br.gov.agu.nutec.msescala.repository;

import br.gov.agu.nutec.msescala.entity.PautaEntity;
import br.gov.agu.nutec.msescala.enums.Uf;
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
    List<PautaEntity> buscarPautasSemAvaliadoresEscaladosPorUf(
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            @Param("uf") Uf uf);


    @Query("SELECT p FROM PautaEntity p WHERE " +
            "p.data BETWEEN :dataInicio AND :dataFim AND " +
            "p.orgaoJulgador.uf.sigla = :uf AND " +
            "p.statusAnaliseComparecimento = br.gov.agu.nutec.msescala.enums.StatusAnaliseComparecimento.COMPARECER AND " +
            "NOT EXISTS (SELECT e FROM EscalaEntity e WHERE e.pauta = p AND e.pautista IS NOT NULL)")
    List<PautaEntity> buscarPautasSemPautistaEscaladosPorUf(
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            @Param("uf") Uf uf);

    @Query("SELECT p FROM PautaEntity p WHERE " +
            "p.data BETWEEN :dataInicio AND :dataFim AND " +
            "NOT EXISTS (SELECT e FROM EscalaEntity e WHERE e.pauta = p AND e.avaliador IS NOT NULL)")
    List<PautaEntity> buscarPautasSemAvaliadoresEscaladosPorPeriodo(LocalDate localDate, LocalDate localDate1);

    @Query("SELECT p FROM PautaEntity p WHERE " +
            "p.data BETWEEN :dataInicio AND :dataFim AND " +
            "p.orgaoJulgador.orgaoJulgadorId IN :orgaoJulgadorIds AND " +
            "NOT EXISTS (SELECT e FROM EscalaEntity e WHERE e.pauta = p AND e.avaliador IS NOT NULL)")
    List<PautaEntity> buscarPautasSemAvaliadoresEscaladosPorOrgaoJulgador(LocalDate localDate, LocalDate localDate1, List<Long> longs);
}