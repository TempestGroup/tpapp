package kz.tempest.tpapp.fileWriter.writers.excel;

import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.models.TemplateInfo;
import kz.tempest.tpapp.commons.utils.LogUtil;
import kz.tempest.tpapp.fileWriter.Writer;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import org.apache.poi.ss.usermodel.*;
import java.util.List;
import java.util.Map;

public class ExcelWriter extends Writer {

    @Override
    public byte[] write(Object obj) {
        return write(obj, null, null);
    }

    @Override
    public byte[] write(Object obj, TemplateInfo templateInfo, Language language) {
        return serialize(obj, templateInfo, language);
    }

    private byte[] serialize(Object obj, TemplateInfo templateInfo, Language language) {
        if (templateInfo == null) {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                Map<String, List<List<Object>>> data = (Map<String, List<List<Object>>>) obj;
                Workbook workbook = new XSSFWorkbook();
                for (Map.Entry<String, List<List<Object>>> entry : data.entrySet()) {
                    Sheet sheet = workbook.createSheet(entry.getKey());
                    int rowCount = 0;
                    for (List<Object> rowData : entry.getValue()) {
                        Row row = sheet.createRow(rowCount++);
                        int columnCount = 0;
                        for (Object field : rowData) {
                            Cell cell = row.createCell(columnCount++);
                            if (field instanceof String) {
                                cell.setCellValue((String) field);
                            } else if (field instanceof Integer) {
                                cell.setCellValue((Integer) field);
                            } else if (field instanceof Double) {
                                cell.setCellValue((Double) field);
                            }
                        }
                    }
                }
                workbook.write(outputStream);
                workbook.close();
                return outputStream.toByteArray();
            } catch (Exception e) {
                LogUtil.write(e);
                return null;
            }
        } else {
            language = (language == null) ? Language.ru : language;
            try (InputStream inputStream = new ByteArrayInputStream(templateInfo.getFiles().get(language).getFile())) {
                Workbook workbook = new XSSFWorkbook(inputStream);
                Map<Object, Map<String, Object>> replacements = (Map<Object, Map<String, Object>>) obj;
                for (Map.Entry<Object, Map<String, Object>> entry : replacements.entrySet()) {
                    Sheet sheet = null;
                    if (entry.getKey() instanceof String name) {
                        sheet = workbook.getSheet(name);
                    } else {
                        sheet = workbook.getSheetAt((Integer) entry.getKey());
                    }
                    if (sheet == null) {
                        continue;
                    }
                    Map<String, Object> data = entry.getValue();
                    for (Row row : sheet) {
                        for (Cell cell : row) {
                            if (cell.getCellType() == CellType.STRING) {
                                String cellValue = getVariableName(cell.getStringCellValue());
                                if (data.containsKey(cellValue)) {
                                    Object value = data.get(cellValue);
                                    if (value instanceof String) {
                                        cell.setCellValue((String) value);
                                    } else if (value instanceof Integer) {
                                        cell.setCellValue((Integer) value);
                                    } else if (value instanceof Double) {
                                        cell.setCellValue((Double) value);
                                    } else if (value instanceof Boolean) {
                                        cell.setCellValue((Boolean) value);
                                    } else if (value != null) {
                                        cell.setCellValue(value.toString());
                                    }
                                }
                            }
                        }
                    }
                }
                try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                    workbook.write(outputStream);
                    workbook.close();
                    return outputStream.toByteArray();
                }
            } catch (Exception e) {
                LogUtil.write(e);
                return null;
            }
        }
    }

    private String getVariableName(String cell) {
        return cell.replace("{{", "").replace("}}", "");
    }
}
