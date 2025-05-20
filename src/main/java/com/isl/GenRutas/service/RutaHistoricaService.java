package com.isl.GenRutas.service;

import com.isl.GenRutas.dto.RutaHistoricaDTO;
import com.isl.GenRutas.model.RutaHistorica;
import com.isl.GenRutas.model.RutaHistoricaLocal;
import com.isl.GenRutas.repository.RutaHistoricaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RutaHistoricaService {

    private final RutaHistoricaRepository rutaHistoricaRepository;

    // Guarda una nueva ruta histórica con lista de códigos
    public void registrarRutaDesdeCodigos(List<String> codigos, String nombreRuta) {
        RutaHistorica ruta = new RutaHistorica();
        ruta.setNombre(nombreRuta);

        List<RutaHistoricaLocal> locales = new ArrayList<>();

        for (int i = 0; i < codigos.size(); i++) {
            RutaHistoricaLocal local = new RutaHistoricaLocal();
            local.setCodigoLocal(codigos.get(i));
            local.setOrden(i + 1);
            local.setRuta(ruta);
            locales.add(local);
        }

        ruta.setLocales(locales);
        rutaHistoricaRepository.save(ruta); // guarda en cascada
    }

    // Retorna las rutas como DTO para frontend
    public List<RutaHistoricaDTO> listarRutasComoDTO() {
        return rutaHistoricaRepository.findAll().stream().map(ruta -> {
            RutaHistoricaDTO dto = new RutaHistoricaDTO();
            dto.setNombre(ruta.getNombre());
            dto.setCodigosLocales(
                    ruta.getLocales().stream()
                            .sorted(Comparator.comparingInt(RutaHistoricaLocal::getOrden))
                            .map(RutaHistoricaLocal::getCodigoLocal)
                            .toList());
            return dto;
        }).toList();
    }

    public List<String> sugerirRutaDesdeHistorial(List<String> codigosPendientes) {
        List<List<String>> sugerencias = sugerirMultiplesRutasDesdeHistorial(codigosPendientes);
        return sugerencias.isEmpty() ? Collections.emptyList() : sugerencias.get(0);
    }

    public List<List<String>> sugerirMultiplesRutasDesdeHistorial(List<String> codigosPendientes) {
        List<RutaHistorica> historicas = rutaHistoricaRepository.findAll();

        List<List<String>> rutasCoincidentes = historicas.stream()
                .map(ruta -> {
                    List<String> codigosRuta = ruta.getLocales().stream()
                            .sorted(Comparator.comparingInt(RutaHistoricaLocal::getOrden))
                            .map(RutaHistoricaLocal::getCodigoLocal)
                            .toList();

                    // Intersección entre ruta histórica y códigos pendientes
                    List<String> interseccion = codigosRuta.stream()
                            .filter(codigosPendientes::contains)
                            .toList();

                    return interseccion;
                })
                .filter(ruta -> !ruta.isEmpty()) // Solo rutas que tengan al menos una coincidencia
                .sorted((a, b) -> Integer.compare(b.size(), a.size())) // Mayor coincidencia primero
                .toList();

        System.out.println("Rutas sugeridas ordenadas:");
        rutasCoincidentes.forEach(System.out::println);

        return rutasCoincidentes;
    }

}
