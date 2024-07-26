package kz.tempest.tpapp.commons.utils;

import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtil {

    public static final String FONTS_DIRECTORY = new ClassPathResource("/assets/fonts/").getPath();

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

    public static byte[] getBytes(MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return null;
            }
            return file.getBytes();
        } catch (Exception ignored) {
            return null;
        }
    }

    public static byte[] getBytes(File file) {
        try {
            if (file == null || !file.exists()) {
                return null;
            }
            return Files.readAllBytes(file.toPath());
        } catch (Exception ignored) {
            return null;
        }
    }

}
