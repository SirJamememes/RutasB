package com.isl.GenRutas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RutaGeneradaLocal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoLocal;
    private Integer orden;

    @ManyToOne
    @JoinColumn(name = "id_ruta")
    private RutaGenerada ruta;
}
