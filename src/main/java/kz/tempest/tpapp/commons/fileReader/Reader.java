package kz.tempest.tpapp.commons.fileReader;

import kz.tempest.tpapp.commons.utils.FileUtil;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public abstract class Reader {
    public final Map<String, Object> fields = new HashMap<>();

    public ReaderType getType() {
        return ReaderType.getByClass(getClass());
    }

    public static Reader getInstance(MultipartFile file) {
        return instance(FileUtil.getFileFormat(file));
    }

    public static Reader getInstance(File file) {
        return instance(FileUtil.getFileFormat(file));
    }

    public static Reader getInstance(String filename) {
        return instance(FileUtil.getFileFormat(filename));
    }

    @SneakyThrows
    public static Reader instance(String fileFormat) {
        return ReaderType.getByFormat(fileFormat).getReader().newInstance();
    }

    public abstract Object read(byte[] fileBytes);
}
