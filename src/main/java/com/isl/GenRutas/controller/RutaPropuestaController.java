package com.isl.GenRutas.controller;

import com.isl.GenRutas.dto.LocalDTO;
import com.isl.GenRutas.service.RutaPropuestaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/propuesta")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RutaPropuestaController {

    private final RutaPropuestaService rutaPropuestaService;

    @PostMapping("/cargar")
    public String cargarLocales(@RequestBody List<String> codigos) {
        rutaPropuestaService.cargarLocalesPendientes(codigos);
        return "Locales pendientes cargados";
    }

    @GetMapping("/sugerir")
    public List<LocalDTO> sugerirRuta() {
        return rutaPropuestaService.sugerirSiguienteRuta();
    }

    @PostMapping("/confirmar")
    public void confirmarRuta(@RequestBody List<String> codigos) {
        rutaPropuestaService.confirmarRuta(codigos);
    }

    @GetMapping("/pendientes")
    public List<String> pendientes() {
        return rutaPropuestaService.getPendientes();
    }

    @GetMapping("/confirmadas")
    public List<List<String>> confirmadas() {
        return rutaPropuestaService.getRutasConfirmadas();
    }

    @PostMapping("/guardar")
    public String guardarEnBD(@RequestParam String codigoCarga,
            @RequestParam(defaultValue = "") String comentario) {
        rutaPropuestaService.guardarRutasConfirmadas(codigoCarga, comentario);
        return "Rutas confirmadas guardadas exitosamente.";
    }

    @GetMapping("/sugerencias")
    public List<List<LocalDTO>> obtenerSugerenciasConDetalles() {
        return rutaPropuestaService.sugerenciasConDetalles();
    }

}
