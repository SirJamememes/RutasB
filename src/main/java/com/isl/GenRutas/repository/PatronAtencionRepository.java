package com.isl.GenRutas.repository;

import com.isl.GenRutas.model.PatronAtencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatronAtencionRepository extends JpaRepository<PatronAtencion, Long> {

}
