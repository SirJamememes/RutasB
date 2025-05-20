package com.isl.GenRutas.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isl.GenRutas.model.RutaGenerada;
import com.isl.GenRutas.model.RutaGeneradaLocal;
import com.isl.GenRutas.repository.RutaGeneradaRepository;

import java.util.*;

@Slf4j
@Service
public class RutaPropuestaService {

    private final List<String> pendientes = new ArrayList<>();
    private final List<List<String>> rutasConfirmadas = new ArrayList<>();

    private final RutaHistoricaService rutaHistoricaService;

    public RutaPropuestaService(RutaHistoricaService rutaHistoricaService) {
        this.rutaHistoricaService = rutaHistoricaService;
    }

    public void cargarLocalesPendientes(List<String> codigos) {
        pendientes.clear();
        pendientes.addAll(codigos);
        rutasConfirmadas.clear();
        log.info("Locales cargados como pendientes: {}", pendientes);
    }

    public List<String> sugerirSiguienteRuta() {
        if (pendientes.isEmpty())
            return Collections.emptyList();
        List<String> sugerida = rutaHistoricaService.sugerirRutaDesdeHistorial(pendientes);
        log.info("Ruta sugerida: {}", sugerida);
        return sugerida;
    }

    public void confirmarRuta(List<String> rutaConfirmada) {
        rutasConfirmadas.add(rutaConfirmada);
        pendientes.removeAll(rutaConfirmada);
        log.info("Ruta confirmada: {}", rutaConfirmada);
        log.info("Pendientes restantes: {}", pendientes);
    }

    public List<List<String>> getRutasConfirmadas() {
        return rutasConfirmadas;
    }

    public List<String> getPendientes() {
        return pendientes;
    }

    @Autowired
    private RutaGeneradaRepository rutaGeneradaRepository;

    public void guardarRutasConfirmadas(String codigoCarga, String comentarioGeneral) {
        int numero = 1;

        for (List<String> codigos : rutasConfirmadas) {
            RutaGenerada ruta = new RutaGenerada();
            ruta.setCodigoCarga(codigoCarga);
            ruta.setNumeroRuta(numero++);
            ruta.setComentario(comentarioGeneral);

            List<RutaGeneradaLocal> locales = new ArrayList<>();
            for (int i = 0; i < codigos.size(); i++) {
                RutaGeneradaLocal local = new RutaGeneradaLocal();
                local.setCodigoLocal(codigos.get(i));
                local.setOrden(i + 1);
                local.setRuta(ruta);
                locales.add(local);
            }

            ruta.setLocales(locales);
            rutaGeneradaRepository.save(ruta);
        }

        log.info("✅ Rutas confirmadas guardadas en base de datos con código: {}", codigoCarga);
    }
}
