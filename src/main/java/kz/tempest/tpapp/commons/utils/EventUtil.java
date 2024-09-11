package kz.tempest.tpapp.commons.utils;

import jakarta.servlet.http.HttpServletRequest;
import kz.tempest.tpapp.commons.contexts.PersonContext;
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

    public static void register(Module module, EventType type, Long objectID, String code, HttpServletRequest request, Object... arguments) {
        register(module, type, objectID, PersonContext.getCurrentPerson(), code, request, arguments);
    }

    public static void register(Module module, EventType type, Long objectID, String contentKK, String contentRU, String contentEN, HttpServletRequest request) {
        register(module, type, objectID, contentKK, contentRU, contentEN, PersonContext.getCurrentPerson(), request);
    }

    public static void register(Module module, EventType type, Long objectID, Person person, String code, HttpServletRequest request, Object... arguments) {
        register(module, type, objectID, TranslateUtil.getMessage(Language.kk, code, arguments), TranslateUtil.getMessage(Language.ru, code, arguments),
                TranslateUtil.getMessage(Language.en, code, arguments), person, HttpUtil.getServerAddress(request));
    }

    public static void register(Module module, EventType type, Long objectID, String contentKK, String contentRU, String contentEN, Person person, HttpServletRequest request) {
        register(module, type, objectID, contentKK, contentRU, contentEN, person, HttpUtil.getServerAddress(request));
    }

    public static void register(Module module, EventType type, Long objectID, String contentKK, String contentRU, String contentEN, Person person, String host) {
        eventInfoService.create(new EventInfo(module, type, person, objectID, contentKK, contentRU, contentEN, host));
    }
}
