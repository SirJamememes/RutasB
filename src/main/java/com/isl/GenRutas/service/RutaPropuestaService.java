package com.isl.GenRutas.service;

import com.isl.GenRutas.dto.LocalDTO;
import com.isl.GenRutas.model.Local;
import com.isl.GenRutas.model.RutaGenerada;
import com.isl.GenRutas.model.RutaGeneradaLocal;
import com.isl.GenRutas.repository.LocalRepository;
import com.isl.GenRutas.repository.RutaGeneradaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class RutaPropuestaService {

    private final List<Local> pendientes = new ArrayList<>();
    private final List<List<String>> rutasConfirmadas = new ArrayList<>();

    private final RutaHistoricaService rutaHistoricaService;
    private final LocalRepository localRepository;
    private final RutaGeneradaRepository rutaGeneradaRepository;

    public RutaPropuestaService(RutaHistoricaService rutaHistoricaService,
            LocalRepository localRepository,
            RutaGeneradaRepository rutaGeneradaRepository) {
        this.rutaHistoricaService = rutaHistoricaService;
        this.localRepository = localRepository;
        this.rutaGeneradaRepository = rutaGeneradaRepository;
    }

    public void cargarLocalesPendientes(List<String> codigos) {
        pendientes.clear();
        rutasConfirmadas.clear();
        pendientes.addAll(localRepository.findByCodigoIn(codigos));
        log.info("Locales cargados como pendientes: {}", pendientes.stream().map(Local::getCodigo).toList());
    }

    public List<LocalDTO> sugerirSiguienteRuta() {
        if (pendientes.isEmpty())
            return Collections.emptyList();

        List<String> codigosPendientes = pendientes.stream()
                .map(Local::getCodigo)
                .toList();

        List<String> sugeridos = rutaHistoricaService.sugerirRutaDesdeHistorial(codigosPendientes);

        List<LocalDTO> resultado = new ArrayList<>();
        for (String codigo : sugeridos) {
            pendientes.stream()
                    .filter(l -> l.getCodigo().equals(codigo))
                    .findFirst()
                    .ifPresent(local -> {
                        LocalDTO dto = new LocalDTO();
                        dto.setCodigo(local.getCodigo());
                        dto.setCodInterno(local.getCodInterno());
                        dto.setNombre(local.getNombre());
                        dto.setZona(local.getZona());
                        resultado.add(dto);
                    });
        }

        log.info("Ruta sugerida: {}", resultado.stream().map(LocalDTO::getCodigo).toList());
        return resultado;
    }

    public void confirmarRuta(List<String> rutaConfirmada) {
        rutasConfirmadas.add(rutaConfirmada);
        pendientes.removeIf(local -> rutaConfirmada.contains(local.getCodigo()));
        log.info("Ruta confirmada: {}", rutaConfirmada);
    }

    public List<List<String>> getRutasConfirmadas() {
        return rutasConfirmadas;
    }

    public List<String> getPendientes() {
        return pendientes.stream().map(Local::getCodigo).toList();
    }

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

    public List<List<String>> obtenerTodasSugerencias() {
        List<String> codigosPendientes = pendientes.stream()
                .map(Local::getCodigo)
                .toList();

        return rutaHistoricaService.sugerirMultiplesRutasDesdeHistorial(codigosPendientes);
    }

    public List<List<LocalDTO>> sugerenciasConDetalles() {
        List<String> codigosPendientes = pendientes.stream()
                .map(Local::getCodigo)
                .toList();

        List<List<String>> sugerenciasBrutas = rutaHistoricaService
                .sugerirMultiplesRutasDesdeHistorial(codigosPendientes);

        List<List<LocalDTO>> resultado = new ArrayList<>();

        for (List<String> sugerencia : sugerenciasBrutas) {
            List<LocalDTO> dtoRuta = new ArrayList<>();

            for (String codigo : sugerencia) {
                pendientes.stream()
                        .filter(l -> l.getCodigo().equals(codigo))
                        .findFirst()
                        .ifPresent(local -> {
                            LocalDTO dto = new LocalDTO();
                            dto.setCodigo(local.getCodigo());
                            dto.setCodInterno(local.getCodInterno());
                            dto.setNombre(local.getNombre());
                            dto.setZona(local.getZona());
                            dtoRuta.add(dto);
                        });
            }

            resultado.add(dtoRuta);
        }

        return resultado;
    }

}
