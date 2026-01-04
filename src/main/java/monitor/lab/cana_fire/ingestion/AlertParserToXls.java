package monitor.lab.cana_fire.ingestion;

import monitor.lab.cana_fire.dto.AlertResponseDto;
import monitor.lab.cana_fire.repository.AlertRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class AlertParserToXls {

    private final AlertRepository alertRepository;

    public AlertParserToXls(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public ByteArrayInputStream exportToXls(List<AlertResponseDto> alertsResponses) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Alerts");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("LAT");
        header.createCell(2).setCellValue("LON");
        header.createCell(3).setCellValue("DATE");

        int rowLines = 1;
        for (AlertResponseDto response : alertsResponses) {
            Row row = sheet.createRow(rowLines);
            row.createCell(0).setCellValue(rowLines); // ID sequencial
            row.createCell(1).setCellValue(response.getLat());
            row.createCell(2).setCellValue(response.getLon());
            row.createCell(3).setCellValue(response.getDate().toString());
            rowLines++;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
}
