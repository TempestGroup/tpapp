package kz.tempest.tpapp.commons.configs;

import kz.tempest.tpapp.commons.filters.DeviceFilter;
import kz.tempest.tpapp.commons.filters.LanguageFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LanguageFilter languageFilter;
    private final DeviceFilter deviceFilter;

    public WebConfig(LanguageFilter languageFilter, DeviceFilter deviceFilter) {
        this.languageFilter = languageFilter;
        this.deviceFilter = deviceFilter;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(languageFilter)
                .addPathPatterns("/api/**");
        registry.addInterceptor(deviceFilter)
                .addPathPatterns("/api/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //  Set the domain name that allows cross domain requests
                //.allowedOrigins("*")
                // Cross domain configuration error , take .allowedOrigins Replace with .allowedOriginPatterns that will do .
                .allowedOriginPatterns("*")
                //  Whether to allow certificates （cookies）
                .allowCredentials(true)
                //  Set allowed methods
                .allowedMethods("*")
                //  Cross domain allow time
                .maxAge(3600);
    }
}
