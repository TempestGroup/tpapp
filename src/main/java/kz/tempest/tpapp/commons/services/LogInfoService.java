package kz.tempest.tpapp.commons.services;

import kz.tempest.tpapp.commons.models.LogInfo;
import kz.tempest.tpapp.commons.repositories.LogInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogInfoService {
    private final LogInfoRepository logInfoRepository;

    public void create(LogInfo logInfo){
        this.logInfoRepository.save(logInfo);
    }
}
