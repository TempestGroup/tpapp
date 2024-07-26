package kz.tempest.tpapp.commons.utils;

import jakarta.servlet.http.HttpServletRequest;
import kz.tempest.tpapp.commons.enums.EventType;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.models.EventInfo;
import kz.tempest.tpapp.commons.enums.Module;
import kz.tempest.tpapp.commons.services.EventInfoService;
import kz.tempest.tpapp.modules.person.models.Person;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class EventUtil {

    public EventUtil (EventInfoService eventInfoService) {
        EventUtil.eventInfoService = eventInfoService;
    }
    private static EventInfoService eventInfoService;

    public static void register(Module module, EventType type, Long objectID, HttpServletRequest request, String code, Object... arguments) {
        register(module, type, objectID, getPerson(), request, code, arguments);
    }

    public static void register(Module module, EventType type, Long objectID, String contentKK, String contentRU, String contentEN, HttpServletRequest request) {
        register(module, type, objectID, contentKK, contentRU, contentEN, getPerson(), request);
    }

    public static void register(Module module, EventType type, Long objectID, Person person, HttpServletRequest request, String code, Object... arguments) {
        register(module, type, objectID, TranslateUtil.getSingleMessage(Language.kk, code, arguments), TranslateUtil.getSingleMessage(Language.ru, code, arguments),
                TranslateUtil.getSingleMessage(Language.en, code, arguments), person, HttpUtil.getServerAddress(request));
    }

    public static void register(Module module, EventType type, Long objectID, String contentKK, String contentRU, String contentEN, Person person, HttpServletRequest request) {
        register(module, type, objectID, contentKK, contentRU, contentEN, person, HttpUtil.getServerAddress(request));
    }

    public static void register(Module module, EventType type, Long objectID, String contentKK, String contentRU, String contentEN, Person person, String host) {
        eventInfoService.create(new EventInfo(module, type, person, objectID, contentKK, contentRU, contentEN, host));
    }

    private static Person getPerson() {
        Person person = null;
        if (SecurityContextHolder.getContext().getAuthentication() != null && !SecurityContextHolder.getContext().getAuthentication().getCredentials().equals("")) {
            person = Person.getPerson(SecurityContextHolder.getContext().getAuthentication());
        }
        return person;
    }
}
