package kz.tempest.tpapp.commons.services;

import kz.tempest.tpapp.commons.constants.CommonMessages;
import kz.tempest.tpapp.commons.dtos.ResponseMessage;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.enums.RMStatus;
import kz.tempest.tpapp.commons.exceptions.FileNotExistException;
import kz.tempest.tpapp.commons.models.IFile;
import kz.tempest.tpapp.commons.repositories.IFileRepository;
import kz.tempest.tpapp.commons.utils.LogUtil;
import kz.tempest.tpapp.commons.utils.TranslateUtil;
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

    public IFile save(MultipartFile file, ResponseMessage message, Language language) {
        try {
            if (file == null || file.isEmpty()) {
                message.set(TranslateUtil.getMessage(CommonMessages.NO_RESOURCES_FOUND, language), RMStatus.ERROR);
                throw new FileNotFoundException();
            }
            message.set(TranslateUtil.getMessage(CommonMessages.SUCCESSFULLY_SAVED, language), RMStatus.SUCCESS);
            return fileRepository.save(new IFile(file.getOriginalFilename(), file.getBytes()));
        } catch (Exception e) {
            message.set(TranslateUtil.getMessage(CommonMessages.NO_RESOURCES_FOUND, language), RMStatus.ERROR);
            LogUtil.write(e);
            return null;
        }
    }

    public void delete(Long id, ResponseMessage message, Language language) {
        try {
            delete(get(id));
            message.set(TranslateUtil.getMessage(CommonMessages.SUCCESSFULLY_DELETED, language), RMStatus.SUCCESS);
        } catch (Exception e) {
            message.set(TranslateUtil.getMessage(CommonMessages.NO_RESOURCES_FOUND, language), RMStatus.SUCCESS);
            LogUtil.write(e);
        }
    }

    public void delete(IFile iFile, ResponseMessage message, Language language) {
        try {
            delete(iFile);
            message.set(TranslateUtil.getMessage(CommonMessages.SUCCESSFULLY_DELETED, language), RMStatus.SUCCESS);
        } catch (Exception e) {
            message.set(TranslateUtil.getMessage(CommonMessages.NO_RESOURCES_FOUND, language), RMStatus.SUCCESS);
            LogUtil.write(e);
        }
    }

    public void delete(IFile iFile) {
        fileRepository.delete(iFile);
    }
}
