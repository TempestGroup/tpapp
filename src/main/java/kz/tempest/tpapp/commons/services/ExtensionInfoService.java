package kz.tempest.tpapp.commons.services;

import kz.tempest.tpapp.commons.repositories.ExtensionInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExtensionInfoService {
    private final ExtensionInfoRepository extensionInfoRepository;


}
