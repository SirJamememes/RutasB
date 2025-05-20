/*package com.isl.GenRutas.loader;

import com.isl.GenRutas.service.RutaHistoricaService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExcelRutaHistoricaLoader implements CommandLineRunner {

    private final RutaHistoricaService rutaHistoricaService;

    @Override
    public void run(String... args) throws Exception {
        // ⚠️ Ruta local del archivo (ajusta esto según tu entorno)
        String excelPath = "src/main/resources/Rutas_reemplazadas_final_corregido.xlsx";

        FileInputStream fis = new FileInputStream(new File(excelPath));
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        int rutaNum = 1;

        for (Row row : sheet) {
            Cell cell = row.getCell(0);
            if (cell == null) continue;

            String contenido = cell.getStringCellValue().trim();
            if (contenido.isEmpty()) continue;

            List<String> codigos = Arrays.stream(contenido.split("-"))
                    .map(String::trim)
                    .filter(c -> !c.isEmpty())
                    .toList();

            rutaHistoricaService.registrarRutaDesdeCodigos(codigos, "RutaHistórica_" + rutaNum++);
        }

        workbook.close();
        fis.close();

        System.out.println("✅ Rutas históricas cargadas exitosamente.");
    }
}*/
