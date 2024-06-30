package kz.tempest.tpapp.commons.configs;

import kz.tempest.tpapp.commons.sources.IMessageSource;
import kz.tempest.tpapp.commons.utils.TranslateUtil;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.io.IOException;

@Configuration
public class TranslationConfig {
//    @Bean
//    public ResourceBundleMessageSource messageSource() {
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasenames("messages");
//        messageSource.setDefaultEncoding("UTF-8");
//        messageSource.setUseCodeAsDefaultMessage(true);
//        TranslateUtil.setMessageSource(messageSource);
//        return messageSource;
//    }

    @Bean
    public MessageSource messageSource() {
        IMessageSource iMessageSource = new IMessageSource();
        TranslateUtil.setMessageSource(iMessageSource);
        return iMessageSource;
    }
}
