package kz.tempest.tpapp.commons.sources;

import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.utils.LogUtil;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Properties;

@Component
public class IMessageSource extends AbstractMessageSource {

    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String FILE_NAME = "messages";
    private static final String FILE_EXTENSION = "properties";
    private final Properties properties;

    public IMessageSource() {
        properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FILE_NAME + "." + FILE_EXTENSION)) {
            if (inputStream != null) {
                try(InputStreamReader reader = new InputStreamReader(inputStream, DEFAULT_ENCODING)) {
                    properties.load(reader);
                }
            }
        } catch (Exception e) {
            LogUtil.write(e);
        }
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        Language language = Language.valueOf(locale.getLanguage());
        if (!Language.contains(locale.getLanguage())) {
            language = Language.ru;
        }
        String messageKey = code + "." + language.name();
        String message = properties.getProperty(messageKey);
        if (!StringUtils.hasText(message)) {
            messageKey = code + "." + Language.ru.name();
            message = properties.getProperty(messageKey);
        }
        return new MessageFormat(message, locale);
    }
}
