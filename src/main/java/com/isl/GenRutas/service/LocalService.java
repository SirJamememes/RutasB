package com.isl.GenRutas.service;

import com.isl.GenRutas.dto.LocalDTO;
import com.isl.GenRutas.model.Local;
import com.isl.GenRutas.model.PatronAtencion;
import com.isl.GenRutas.repository.LocalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class LocalService {

    private final LocalRepository localRepository;

    public Map<String, List<LocalDTO>> filtrarPorFrecuencia(List<String> codigosLocales, LocalDate fechaCarga) {
        LocalDate fechaEntrega = fechaCarga.plusDays(2);
        DayOfWeek diaSemana = fechaEntrega.getDayOfWeek();

        List<Local> locales = localRepository.findByCodigoIn(codigosLocales);

        List<LocalDTO> enFrecuencia = new ArrayList<>();
        List<LocalDTO> fueraFrecuencia = new ArrayList<>();

        for (Local local : locales) {
            PatronAtencion patron = local.getPatronAtencion();
            if (patron == null) {
                fueraFrecuencia.add(convertirALocalDTO(local));
                continue;
            }

            boolean estaEnFrecuencia = switch (diaSemana) {
                case MONDAY -> patron.isLunes();
                case TUESDAY -> patron.isMartes();
                case WEDNESDAY -> patron.isMiercoles();
                case THURSDAY -> patron.isJueves();
                case FRIDAY -> patron.isViernes();
                case SATURDAY -> patron.isSabado();
                case SUNDAY -> patron.isDomingo();
            };

            if (estaEnFrecuencia) {
                enFrecuencia.add(convertirALocalDTO(local));
            } else {
                fueraFrecuencia.add(convertirALocalDTO(local));
            }
        }

        Map<String, List<LocalDTO>> resultado = new HashMap<>();
        resultado.put("enFrecuencia", enFrecuencia);
        resultado.put("fueraFrecuencia", fueraFrecuencia);

        return resultado;
    }

    private LocalDTO convertirALocalDTO(Local local) {
        LocalDTO dto = new LocalDTO();
        dto.setCodigo(local.getCodigo());
        dto.setCodInterno(local.getCodInterno());
        dto.setNombre(local.getNombre());
        dto.setZona(local.getZona());
        return dto;
    }
}
