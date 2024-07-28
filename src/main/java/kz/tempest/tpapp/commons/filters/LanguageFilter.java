package kz.tempest.tpapp.commons.filters;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.tempest.tpapp.commons.contexts.LanguageContext;
import kz.tempest.tpapp.commons.enums.Language;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Locale;

@Component
public class LanguageFilter implements HandlerInterceptor {

    public static final Language DEFAULT_LANGUAGE = Language.valueOf(Locale.getDefault().getLanguage());
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String code = request.getHeader("Language");
        if (code != null) {
            try {
                LanguageContext.setLanguage(Language.valueOf(code));
            } catch (Exception e) {
                LanguageContext.setLanguage(DEFAULT_LANGUAGE);
            }
        } else {
            LanguageContext.setLanguage(DEFAULT_LANGUAGE);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        LanguageContext.clear();
    }
}
