package kz.tempest.tpapp.commons.fileReader;

import kz.tempest.tpapp.commons.utils.ClassUtil;
import kz.tempest.tpapp.commons.utils.FileUtil;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Data
public abstract class Reader {
    public final Map<String, Object> fields = new HashMap<>();
    public static byte[] bytes;

    public ReaderType getType() {
        return ReaderType.getByClass(getClass());
    }

    public static <T extends Reader> T getInstance(MultipartFile file) {
        bytes = FileUtil.getBytes(file);
        return instance(FileUtil.getFileFormat(file));
    }

    public static <T extends Reader> T getInstance(File file) {
        bytes = FileUtil.getBytes(file);
        return instance(FileUtil.getFileFormat(file));
    }

    public static <T extends Reader> T getInstance(String filename, byte[] bytes) {
        Reader.bytes = bytes;
        return instance(FileUtil.getFileFormat(filename));
    }

    public static <T extends Reader> T getInstance(String filename) {
        Reader.bytes = new byte[0];
        return instance(FileUtil.getFileFormat(filename));
    }

    public static <T extends Reader> T instance(String fileFormat) {
        return ClassUtil.cast(ClassUtil.newInstance(ReaderType.getByFormat(fileFormat).getReader()), (Class<T>) ReaderType.getByFormat(fileFormat).getReader());
    }

    public abstract Object read();
}
