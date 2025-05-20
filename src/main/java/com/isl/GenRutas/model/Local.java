package com.isl.GenRutas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "local")
@Getter
@Setter
public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_local")
    private Long id;

    @Column(length = 255)
    private String codigo;

    @Column(length = 255)
    private String codInterno;  // NUEVO CAMPO

    private String direccion;
    private String nombre;
    private String zona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_patron_atencion")
    private PatronAtencion patronAtencion;
}
