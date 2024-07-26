package kz.tempest.tpapp.fileWriter;

import kz.tempest.tpapp.fileReader.Reader;
import kz.tempest.tpapp.fileReader.ReaderType;
import kz.tempest.tpapp.fileReader.readers.*;
import kz.tempest.tpapp.fileWriter.writers.doc.DocWriter;
import kz.tempest.tpapp.fileWriter.writers.excel.ExcelWriter;
import kz.tempest.tpapp.fileWriter.writers.json.JsonWriter;
import kz.tempest.tpapp.fileWriter.writers.pdf.PdfWriter;
import kz.tempest.tpapp.fileWriter.writers.txt.TxtWriter;
import kz.tempest.tpapp.fileWriter.writers.xml.XmlWriter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum WriterType {
    TXT(TxtWriter.class, ".txt"),
    JSON(JsonWriter.class, ".json"),
    XML(XmlWriter.class, ".xml"),
    DOC(DocWriter.class, ".docx"),
    PDF(PdfWriter.class, ".pdf"),
    EXCEL(ExcelWriter.class, ".xls");

    private final Class<? extends Writer> writer;
    private final String format;

    WriterType(Class<? extends Writer> writer, String format) {
        this.writer = writer;
        this.format = format;
    }

    private static final Map<Class<? extends Writer>, WriterType> byClass = new HashMap<>();
    private static final Map<String, WriterType> byFormat = new HashMap<>();

    static {
        for (WriterType type : values()) {
            byClass.put(type.writer, type);
            byFormat.put(type.format, type);
        }
    }

    public static WriterType getByClass(Class readerClass) {
        return byClass.get(readerClass);
    }

    public static WriterType getByFormat(String format) {
        return byFormat.get(format);
    }

    public Class<? extends Writer> getWriter() {
        return writer;
    }

    public String getFormat() {
        return format;
    }
}
