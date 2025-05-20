package com.isl.GenRutas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class RutaGenerada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoCarga;

    private Integer numeroRuta;

    private String comentario;

    @OneToMany(mappedBy = "ruta", cascade = CascadeType.ALL)
    private List<RutaGeneradaLocal> locales;
}
