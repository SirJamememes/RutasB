package com.isl.GenRutas.repository;

import com.isl.GenRutas.model.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {

    Optional<Local> findByCodigo(String codigo);

    List<Local> findByCodigoIn(List<String> codigos); // útil para filtrar por carga

}
