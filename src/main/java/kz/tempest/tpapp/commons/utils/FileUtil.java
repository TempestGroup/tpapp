package kz.tempest.tpapp.commons.utils;

import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;

public class FileUtil {

    public static String getFileFormat(String filename) {
        if (filename == null || filename.lastIndexOf('.') == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf('.'));
    }

    public static String getFileFormat(MultipartFile file) {
        return getFileFormat(file.getOriginalFilename());
    }

    public static String getFileFormat(File file) {
        return getFileFormat(file.getName());
    }

    @SneakyThrows
    public static byte[] getBytes(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        return file.getBytes();
    }

    @SneakyThrows
    public static byte[] getBytes(File file) {
        if (file == null || !file.exists()) {
            return null;
        }
        return Files.readAllBytes(file.toPath());
    }

}
