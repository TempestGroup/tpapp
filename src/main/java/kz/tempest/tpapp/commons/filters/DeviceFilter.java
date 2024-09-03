package kz.tempest.tpapp.commons.filters;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.tempest.tpapp.commons.contexts.DeviceContext;
import kz.tempest.tpapp.commons.contexts.LanguageContext;
import kz.tempest.tpapp.commons.enums.DeviceType;
import kz.tempest.tpapp.commons.enums.Language;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Locale;

@Component
public class DeviceFilter implements HandlerInterceptor {

    public static final DeviceType DEFAULT_TYPE = DeviceType.WEB;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String type = request.getHeader("Device-Type");
        if (type != null) {
            try {
                DeviceContext.setDeviceType(DeviceType.valueOf(type.toUpperCase()));
            } catch (Exception e) {
                DeviceContext.setDeviceType(DEFAULT_TYPE);
            }
        } else {
            DeviceContext.setDeviceType(DEFAULT_TYPE);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        DeviceContext.clear();
    }

}
