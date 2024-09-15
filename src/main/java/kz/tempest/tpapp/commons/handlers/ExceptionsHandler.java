package kz.tempest.tpapp.commons.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.tempest.tpapp.commons.constants.CommonMessages;
import kz.tempest.tpapp.commons.dtos.Response;
import kz.tempest.tpapp.commons.dtos.ResponseMessage;
import kz.tempest.tpapp.commons.enums.RMStatus;
import kz.tempest.tpapp.commons.exceptions.UnauthorizedException;
import kz.tempest.tpapp.commons.utils.LogUtil;
import kz.tempest.tpapp.commons.utils.StringUtil;
import kz.tempest.tpapp.commons.utils.TranslateUtil;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(Exception.class)
    public Response handleExceptions(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        ResponseMessage message;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (exception instanceof AccessDeniedException) {
            message = new ResponseMessage(TranslateUtil.getMessage(CommonMessages.ACCESS_DENIED), RMStatus.ERROR);
            status = HttpStatus.LOCKED;
        } else if (exception instanceof UnauthorizedException) {
            message = new ResponseMessage(TranslateUtil.getMessage(CommonMessages.YOU_ARENT_AUTHORIZED), RMStatus.ERROR);
            status = HttpStatus.UNAUTHORIZED;
        } else if (exception instanceof DisabledException) {
            message = new ResponseMessage(TranslateUtil.getMessage(CommonMessages.USER_IS_DISABLED), RMStatus.ERROR);
            status = HttpStatus.BAD_REQUEST;
        } else if (exception instanceof BadCredentialsException) {
            message = new ResponseMessage(TranslateUtil.getMessage(CommonMessages.USERNAME_OR_PASSWORD_IS_WRONG), RMStatus.ERROR);
            status = HttpStatus.BAD_REQUEST;
        } else if (exception instanceof MissingPathVariableException) {
            message = new ResponseMessage(TranslateUtil.getMessage(CommonMessages.ENTITY_IS_NOT_FOUND), RMStatus.ERROR);
            status = HttpStatus.BAD_REQUEST;
        } else if (exception instanceof NullPointerException) {
            if (exception.getMessage() != null && exception.getMessage().contains(Authentication.class.getName())) {
                message = new ResponseMessage(TranslateUtil.getMessage(CommonMessages.FAILED_AUTHENTICATION), RMStatus.ERROR);
                status = HttpStatus.NETWORK_AUTHENTICATION_REQUIRED;
            } else {
                message = new ResponseMessage(TranslateUtil.getMessage(CommonMessages.NULL_POINTER), RMStatus.ERROR);
                status = HttpStatus.NOT_FOUND;
            }
        } else if (exception instanceof NoResourceFoundException) {
            message = new ResponseMessage(TranslateUtil.getMessage(CommonMessages.NO_RESOURCES_FOUND), RMStatus.ERROR);
            status = HttpStatus.NOT_FOUND;
        } else if (exception instanceof HttpMessageNotReadableException) {
            message = new ResponseMessage(TranslateUtil.getMessage(CommonMessages.CANNOT_PARSE_DATA), RMStatus.ERROR);
            status = HttpStatus.BAD_REQUEST;
        } else if (exception instanceof MissingRequestHeaderException) {
            message = new ResponseMessage(TranslateUtil.getMessage(CommonMessages.MISSING_REQUEST_HEADER), RMStatus.ERROR);
            status = HttpStatus.BAD_REQUEST;
        } else if (exception instanceof UsernameNotFoundException) {
            message = new ResponseMessage(TranslateUtil.getMessage(CommonMessages.USER_NOT_FOUND), RMStatus.ERROR);
            status = HttpStatus.NOT_FOUND;
        } else if (exception instanceof InternalAuthenticationServiceException) {
            message = new ResponseMessage(TranslateUtil.getMessage(CommonMessages.USER_IS_NOT_EXIST), RMStatus.ERROR);
            status = HttpStatus.NOT_FOUND;
        } else if (exception instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
            List<String> content = methodArgumentNotValidException.getBindingResult().getFieldErrors()
                    .stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            message = new ResponseMessage(StringUtil.join(content, "\n"), RMStatus.ERROR);
            status = HttpStatus.BAD_REQUEST;
        } else if (exception instanceof MethodArgumentTypeMismatchException) {
            message = new ResponseMessage(TranslateUtil.getMessage(CommonMessages.LANGUAGE_IS_NOT_EXIST), RMStatus.ERROR);
            status = HttpStatus.BAD_REQUEST;
        } else if (exception instanceof NoSuchElementException) {
            message = new ResponseMessage(TranslateUtil.getMessage(CommonMessages.USER_NOT_FOUND), RMStatus.ERROR);
            status = HttpStatus.NOT_FOUND;
        } else {
            message = new ResponseMessage(TranslateUtil.getMessage(CommonMessages.ERROR), RMStatus.ERROR);
        }
        LogUtil.write(exception);
        return Response.getResponse("message", message, status);
    }

}
