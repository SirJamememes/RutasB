package com.isl.GenRutas.controller;

import com.isl.GenRutas.dto.RutaHistoricaDTO;
import com.isl.GenRutas.service.RutaHistoricaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rutas-historicas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RutaHistoricaController {

    private final RutaHistoricaService rutaHistoricaService;

    @GetMapping
    public List<RutaHistoricaDTO> listarRutas() {
        return rutaHistoricaService.listarRutasComoDTO();
    }

    @PostMapping("/sugerir")
    public List<String> sugerirRuta(@RequestBody List<String> codigosPendientes) {
        return rutaHistoricaService.sugerirRutaDesdeHistorial(codigosPendientes);
    }

}
