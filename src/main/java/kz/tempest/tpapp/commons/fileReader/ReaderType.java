package kz.tempest.tpapp.commons.fileReader;

import kz.tempest.tpapp.commons.fileReader.readers.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ReaderType {
    TXT(TxtReader.class, List.of(".txt")),
    JSON(JsonReader.class, List.of(".json")),
    XML(XmlReader.class, List.of(".xml")),
    DOC(DocReader.class, List.of(".doc", ".docx")),
    EXCEL(ExcelReader.class, List.of(".xlsx", ".xls"));

    private final Class<? extends Reader> reader;
    private final List<String> formats;

    private static final Map<Class<? extends Reader>, ReaderType> byClass = new HashMap<>();
    private static final Map<String, ReaderType> byFormats = new HashMap<>();

    static {
        for (ReaderType type : values()) {
            byClass.put(type.reader, type);
            for(String format : type.formats) {
                byFormats.put(format, type);
            }
        }
    }

    ReaderType(Class<? extends Reader> reader, List<String> formats) {
        this.reader = reader;
        this.formats = formats;
    }

    public static ReaderType getByClass(Class readerClass) {
        return byClass.get(readerClass);
    }

    public static ReaderType getByFormat(String format) {
        return byFormats.get(format);
    }

    public Class<? extends Reader> getReader() {
        return reader;
    }

    public List<String> getFormats() {
        return formats;
    }
}
