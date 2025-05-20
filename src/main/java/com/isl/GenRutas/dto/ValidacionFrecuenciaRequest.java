package com.isl.GenRutas.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ValidacionFrecuenciaRequest {
    private List<String> codigosLocales;
    private LocalDate fechaCarga;
}
