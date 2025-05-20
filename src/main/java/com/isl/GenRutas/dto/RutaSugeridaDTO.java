package com.isl.GenRutas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RutaSugeridaDTO {
    private List<String> codigosLocales; // códigos sugeridos en orden
}
