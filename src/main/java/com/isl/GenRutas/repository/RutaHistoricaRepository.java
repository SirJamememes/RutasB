package com.isl.GenRutas.repository;

import com.isl.GenRutas.model.RutaHistorica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RutaHistoricaRepository extends JpaRepository<RutaHistorica, Long> {
}
