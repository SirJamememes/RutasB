package com.isl.GenRutas.repository;

import com.isl.GenRutas.model.RutaHistoricaLocal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RutaHistoricaLocalRepository extends JpaRepository<RutaHistoricaLocal, Long> {
}
