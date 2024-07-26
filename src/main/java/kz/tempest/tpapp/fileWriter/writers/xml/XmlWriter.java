package kz.tempest.tpapp.fileWriter.writers.xml;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.models.TemplateInfo;
import kz.tempest.tpapp.commons.utils.LogUtil;
import kz.tempest.tpapp.fileWriter.Writer;

public class XmlWriter extends Writer {

    @Override
    public byte[] write(Object obj) {
        return write(obj, null, null);
    }

    @Override
    public byte[] write(Object obj, TemplateInfo templateInfo, Language language) {
        return serialize(obj);
    }

    private byte[] serialize(Object obj) {
        try {
            return new XmlMapper().writeValueAsBytes(obj);
        } catch (Exception e) {
            LogUtil.write(e);
            return new byte[0];
        }
    }
}
