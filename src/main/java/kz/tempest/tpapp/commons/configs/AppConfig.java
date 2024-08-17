package kz.tempest.tpapp.commons.configs;

import kz.tempest.tpapp.commons.sources.IMessageSource;
import kz.tempest.tpapp.commons.utils.TranslateUtil;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MessageSource messageSource() {
        IMessageSource iMessageSource = new IMessageSource();
        TranslateUtil.setMessageSource(iMessageSource);
        return iMessageSource;
    }
}
