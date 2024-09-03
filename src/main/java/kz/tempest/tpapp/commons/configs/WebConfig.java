package kz.tempest.tpapp.commons.configs;

import kz.tempest.tpapp.commons.filters.DeviceFilter;
import kz.tempest.tpapp.commons.filters.LanguageFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    public WebConfig(LanguageFilter languageFilter, DeviceFilter deviceFilter) {
        this.languageFilter = languageFilter;
        this.deviceFilter = deviceFilter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
    }

    private final LanguageFilter languageFilter;
    private final DeviceFilter deviceFilter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(languageFilter);
        registry.addInterceptor(deviceFilter);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/favicon.ico")
                .addResourceLocations("classpath:/static/favicon.ico");
    }
}
