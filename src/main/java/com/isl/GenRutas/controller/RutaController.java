package com.isl.GenRutas.controller;

import com.isl.GenRutas.service.RutaHistoricaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rutas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RutaController {

    private final RutaHistoricaService rutaHistoricaService;

    @PostMapping("/sugerir")
    public List<String> sugerirRuta(@RequestBody List<String> localesDisponibles) {
        return rutaHistoricaService.sugerirRutaDesdeHistorial(localesDisponibles);
    }
}
