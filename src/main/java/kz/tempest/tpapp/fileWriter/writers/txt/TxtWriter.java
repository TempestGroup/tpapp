package kz.tempest.tpapp.fileWriter.writers.txt;

import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.models.TemplateInfo;
import kz.tempest.tpapp.fileWriter.Writer;

import java.nio.charset.StandardCharsets;

public class TxtWriter extends Writer {

    @Override
    public byte[] write(Object obj) {
        return write(obj, null, null);
    }

    @Override
    public byte[] write(Object obj, TemplateInfo templateInfo, Language language) {
        return serialize(obj);
    }

    private byte[] serialize(Object obj) {
        return obj.toString().getBytes(StandardCharsets.UTF_8);
    }
}
