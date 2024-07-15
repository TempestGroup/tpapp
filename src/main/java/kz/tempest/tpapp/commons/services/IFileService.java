package kz.tempest.tpapp.commons.services;

import kz.tempest.tpapp.commons.models.IFile;
import kz.tempest.tpapp.commons.repositories.IFileRepository;
import kz.tempest.tpapp.commons.utils.LogUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class IFileService {
    private final IFileRepository fileRepository;

    public IFile get(Long id) throws Exception {
        return fileRepository.findById(id).orElseThrow(() -> {
            Exception e = new RuntimeException("File with id '#" + id + "' isn't exist!");
            LogUtil.write(e);
            return e;
        });
    }

    public IFile save(MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                LogUtil.write(new RuntimeException("File is empty!"));
                return null;
            }
            return fileRepository.save(new IFile(file.getOriginalFilename(), file.getBytes()));
        } catch (IOException e) {
            LogUtil.write(e);
            return null;
        }
    }

    public void delete(Long id) {
        try {
            delete(get(id));
        } catch (Exception e) {
            LogUtil.write(e);
        }
    }

    public void delete(IFile iFile) {
        fileRepository.delete(iFile);
    }
}
