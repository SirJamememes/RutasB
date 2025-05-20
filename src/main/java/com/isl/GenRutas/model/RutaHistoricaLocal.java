package com.isl.GenRutas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ruta_historica_local")
@Getter
@Setter
public class RutaHistoricaLocal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoLocal;

    private int orden;

    @ManyToOne
    @JoinColumn(name = "id_ruta")
    private RutaHistorica ruta;
}
