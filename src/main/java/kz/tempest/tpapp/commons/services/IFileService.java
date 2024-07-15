package kz.tempest.tpapp.commons.services;

import kz.tempest.tpapp.commons.exceptions.FileNotExistException;
import kz.tempest.tpapp.commons.models.IFile;
import kz.tempest.tpapp.commons.repositories.IFileRepository;
import kz.tempest.tpapp.commons.utils.LogUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileNotFoundException;

@Service
@RequiredArgsConstructor
public class IFileService {
    private final IFileRepository fileRepository;

    public IFile get(Long id) {
        try {
            return fileRepository.findById(id).orElseThrow(() -> new FileNotExistException(id));
        } catch (Exception e) {
            LogUtil.write(e);
            return null;
        }
    }

    public IFile save(MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                throw new FileNotFoundException();
            }
            return fileRepository.save(new IFile(file.getOriginalFilename(), file.getBytes()));
        } catch (Exception e) {
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
