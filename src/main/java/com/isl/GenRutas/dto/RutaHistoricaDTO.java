package com.isl.GenRutas.dto;

import lombok.Data;

import java.util.List;

@Data
public class RutaHistoricaDTO {
    private String nombre;
    private List<String> codigosLocales; // en orden
}
