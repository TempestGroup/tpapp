package kz.tempest.tpapp.commons.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.Objects;

public class HttpUtil {
    public static String getClientIP() {
        if (RequestContextHolder.getRequestAttributes() != null) {
            return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getRemoteAddr();
        }
        return "0.0.0.0";
    }

    public static String getServerAddress() {
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            return getServerAddress(request);
        }
        return "0.0.0.0";
    }

    public static String getServerAddress(HttpServletRequest request) {
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromRequest(request);
        return builder.build().getHost();
    }

    public static int getServerPort() {
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            return getServerPort(request);
        }
        return 8080;
    }

    public static int getServerPort(HttpServletRequest request) {
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromRequest(request);
        return builder.build().getPort();
    }

    public static String getDomain() {
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            return getDomain(request);
        }
        return "localhost:8080";
    }

    public static String getDomain(HttpServletRequest request) {
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromRequest(request);
        return builder.build().getHost();
    }

    public static String getHostName() {
        return getDomain() + ((getServerPort() != 80) ?  ":" + getServerPort() : "");
    }
}
