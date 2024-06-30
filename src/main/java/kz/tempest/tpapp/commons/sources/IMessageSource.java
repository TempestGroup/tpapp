package kz.tempest.tpapp.commons.sources;

import kz.tempest.tpapp.commons.utils.LogUtil;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Properties;

public class IMessageSource extends AbstractMessageSource {
    private final Properties properties;

    public IMessageSource() {
        properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("messages.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new IOException("Property file 'messages.properties' not found in the classpath");
            }
        } catch (Exception e) {
            LogUtil.write(e);
        }
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String messageKey = code + "." + locale.getLanguage();
        String message = properties.getProperty(messageKey);
        if (!StringUtils.hasText(message)) {
            messageKey = code + ".ru";
            message = properties.getProperty(messageKey);
        }
        return new MessageFormat(message, locale);
    }

    public String[] getMessages(String code, Locale locale) {
        String messageKey = code + "." + locale.getLanguage();
        String message = properties.getProperty(messageKey);
        if (!StringUtils.hasText(message)) {
            messageKey = code + ".ru";
            message = properties.getProperty(messageKey);
        }
        return message.split(",");
    }
}
