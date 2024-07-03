package kz.tempest.tpapp.commons.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.tempest.tpapp.commons.utils.TokenUtil;
import kz.tempest.tpapp.modules.person.models.Person;
import kz.tempest.tpapp.modules.person.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {
    private final PersonService personService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().contains("/api/v1/auth/confirm") ||
                request.getServletPath().equals("/api/v1/auth/register") ||
                (request.getServletPath().equals("/api/v1/auth/login") && request.getMethod().equals(HttpMethod.POST.name()))
        ) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = "";
        if (request.getHeader("Token") != null && !request.getHeader("Token").isEmpty()) {
            token = request.getHeader("Token");
        }
        if (!token.isEmpty() && TokenUtil.validateToken(token)) {
            Person person = parse(token);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(person, person.getUsername(), person.getRoles()));
        }
        filterChain.doFilter(request, response);
    }

    public Person parse (String token){
        return personService.loadUserByUsername(TokenUtil.getUsernameFromToken(token));
    }
}
