package kz.tempest.tpapp.fileReader.readers;

import kz.tempest.tpapp.fileReader.Reader;
import kz.tempest.tpapp.commons.utils.LogUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelReader extends Reader {
    @Override
    public Map<String,List<List<String>>> read() {
        Map<String, List<List<String>>> sheets = new HashMap<>();
        try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(bytes))) {
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                List<List<String>> sheetData = new ArrayList<>();
                Sheet sheet = workbook.getSheetAt(i);
                for (Row row : sheet) {
                    Iterator<Cell> cellIterator = row.cellIterator();
                    List<String> rowData = new ArrayList<>();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        rowData.add(getCellValueAsString(cell));
                    }
                    sheetData.add(rowData);
                }
                sheets.put(sheet.getSheetName(), sheetData);
            }
        } catch (IOException e) {
            LogUtil.write(e);
        }
        return sheets;
    }

    private String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING -> {
                return cell.getStringCellValue();
            }
            case NUMERIC -> {
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            }
            case BOOLEAN -> {
                return String.valueOf(cell.getBooleanCellValue());
            }
            case FORMULA -> {
                return cell.getCellFormula();
            }
            default -> {
                return "";
            }
        }
    }
}
