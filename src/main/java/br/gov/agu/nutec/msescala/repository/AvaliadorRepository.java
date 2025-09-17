package br.gov.agu.nutec.msescala.repository;

import br.gov.agu.nutec.msescala.entity.AvaliadorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AvaliadorRepository extends JpaRepository<AvaliadorRepository, Long> {

    @Query("SELECT a FROM AvaliadorEntity a " +
           "WHERE a.afastado = false " +
           "AND a.escalaAutomatica = true " +
           "AND NOT EXISTS (" +
           "    SELECT e FROM EscalaEntity e " +
           "    WHERE e.avaliador = a " +
           "    AND e.pauta.data = :data" +
           ")")
    List<AvaliadorEntity> buscarAvaliadoresDisponveis(@Param("data") LocalDate data);
}
