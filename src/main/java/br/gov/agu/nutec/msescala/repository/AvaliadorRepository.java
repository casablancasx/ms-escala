package br.gov.agu.nutec.msescala.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliadorRepository extends JpaRepository<AvaliadorRepository, Long> {
}
