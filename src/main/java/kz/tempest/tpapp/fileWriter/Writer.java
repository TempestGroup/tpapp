package kz.tempest.tpapp.fileWriter;

import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.models.TemplateInfo;
import kz.tempest.tpapp.commons.utils.ClassUtil;
import lombok.Data;
import java.util.HashMap;
import java.util.Map;

@Data
public abstract class Writer {
    public final Map<String, Object> fields = new HashMap<>();

    public WriterType getType() {
        return WriterType.getByClass(getClass());
    }

    public static <T extends Writer> T getInstance(WriterType type) {
        return instance(type);
    }

    public static <T extends Writer> T instance(WriterType type) {
        return ClassUtil.cast(ClassUtil.newInstance(type.getWriter()), (Class<T>) type.getWriter());
    }

    public abstract byte[] write(Object obj, TemplateInfo templateInfo, Language language);

    public abstract byte[] write(Object obj);
}
