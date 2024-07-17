package kz.tempest.tpapp.commons.fileReader;

import kz.tempest.tpapp.commons.utils.FileUtil;
import lombok.Data;
import lombok.SneakyThrows;
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

    @SneakyThrows
    public static Reader getInstance(MultipartFile file) {
        bytes = FileUtil.getBytes(file);
        return instance(FileUtil.getFileFormat(file));
    }

    public static Reader getInstance(File file) {
        bytes = FileUtil.getBytes(file);
        return instance(FileUtil.getFileFormat(file));
    }

    public static Reader getInstance(String filename, byte[] bytes) {
        Reader.bytes = bytes;
        return instance(FileUtil.getFileFormat(filename));
    }

    public static Reader getInstance(String filename) {
        Reader.bytes = new byte[0];
        return instance(FileUtil.getFileFormat(filename));
    }

    @SneakyThrows
    public static Reader instance(String fileFormat) {
        return ReaderType.getByFormat(fileFormat).getReader().newInstance();
    }

    public abstract Object read();
}
