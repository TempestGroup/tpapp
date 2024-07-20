package kz.tempest.tpapp.commons.utils;

import ch.qos.logback.classic.joran.action.LevelAction;
import kz.tempest.tpapp.commons.enums.LogType;
import kz.tempest.tpapp.commons.models.LogInfo;
import kz.tempest.tpapp.commons.services.LogInfoService;
import kz.tempest.tpapp.modules.person.models.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class LogUtil {

    public LogUtil (LogInfoService logInfoService) {
        LogUtil.logInfoService = logInfoService;
    }
    private static LogInfoService logInfoService;
    private static final Logger logger = LoggerFactory.getLogger("TempestApp");

    public static void write(String content, LogType type) {
        Person person = null;
        if (SecurityContextHolder.getContext().getAuthentication() != null && !SecurityContextHolder.getContext().getAuthentication().getCredentials().equals("")) {
            person = Person.getPerson(SecurityContextHolder.getContext().getAuthentication());
        }
        logInfoService.create(new LogInfo(content, type, person));
        if (type == LogType.INFO) {
            logger.info(content);
        } else if (type == LogType.WARNING) {
            logger.warn(content);
        } else if (type == LogType.ERROR){
            logger.error(content);
        }
    }

    public static void write(Exception exception) {
        Person person = null;
        if (SecurityContextHolder.getContext().getAuthentication() != null && !SecurityContextHolder.getContext().getAuthentication().getCredentials().equals("")) {
            person = Person.getPerson(SecurityContextHolder.getContext().getAuthentication());
        }
        logInfoService.create(new LogInfo(exception.getMessage(), LogType.ERROR, person));
        exception.printStackTrace();
    }

}
