package kz.tempest.tpapp.commons.services;

import kz.tempest.tpapp.commons.dtos.EventInfoResponse;
import kz.tempest.tpapp.commons.dtos.PageObject;
import kz.tempest.tpapp.commons.dtos.SearchFilter;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.models.EventInfo;
import kz.tempest.tpapp.commons.repositories.EventInfoRepository;
import kz.tempest.tpapp.commons.specifications.EventInfoSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventInfoService {
    private final EventInfoRepository eventInfoRepository;

    public void create(EventInfo eventInfo) {
        this.eventInfoRepository.save(eventInfo);
    }

    public PageObject<EventInfoResponse> getEvents(SearchFilter searchFilter, Language language) {
        return new PageObject<>(eventInfoRepository.findAll(EventInfoSpecification.getSpecification(searchFilter.getFilter()),
                searchFilter.getPageable(EventInfo.class, language)).map(event -> EventInfoResponse.from(event, language)));
    }
}
