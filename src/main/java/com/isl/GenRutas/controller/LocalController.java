package com.isl.GenRutas.controller;

import com.isl.GenRutas.dto.LocalDTO;
import com.isl.GenRutas.dto.ValidacionFrecuenciaRequest;
import com.isl.GenRutas.service.LocalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/locales")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LocalController {

    private final LocalService localService;

    @PostMapping("/validar-frecuencia")
    public Map<String, List<LocalDTO>> validarFrecuencia(@RequestBody ValidacionFrecuenciaRequest request) {
        return localService.filtrarPorFrecuencia(request.getCodigosLocales(), request.getFechaCarga());
    }
}
