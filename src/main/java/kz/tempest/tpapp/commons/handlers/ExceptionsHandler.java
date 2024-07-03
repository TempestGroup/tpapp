package kz.tempest.tpapp.commons.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.tempest.tpapp.commons.constants.CommonMessages;
import kz.tempest.tpapp.commons.dtos.Response;
import kz.tempest.tpapp.commons.dtos.ResponseMessage;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.enums.ResponseMessageStatus;
import kz.tempest.tpapp.commons.utils.StringUtil;
import kz.tempest.tpapp.commons.utils.TranslateUtil;
import org.springframework.http.ResponseEntity;
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
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(Exception.class)
    public static ResponseEntity<Response> handleExceptions (HttpServletRequest request, HttpServletResponse response, Exception exception) {
        Language language = (StringUtil.isNotEmpty(request.getHeader("Language")) && Language.contains(request.getHeader("Language"))) ?
                Language.valueOf(request.getHeader("Language"))   :
                Language.ru;
        ResponseMessage message;
        if (exception instanceof AccessDeniedException) {
            message = new ResponseMessage(TranslateUtil.getMessage(CommonMessages.ACCESS_DENIED, language), ResponseMessageStatus.ERROR);
        } else if (exception instanceof DisabledException) {
            message = new ResponseMessage(TranslateUtil.getMessage(CommonMessages.USER_IS_DISABLED, language), ResponseMessageStatus.ERROR);
        } else if (exception instanceof BadCredentialsException) {
            message = new ResponseMessage(TranslateUtil.getMessage(CommonMessages.USERNAME_OR_PASSWORD_IS_WRONG, language), ResponseMessageStatus.ERROR);
        } else if (exception instanceof MissingPathVariableException) {
            message = new ResponseMessage(TranslateUtil.getMessage(CommonMessages.ENTITY_IS_NOT_FOUND, language), ResponseMessageStatus.ERROR);
        } else if (exception instanceof NullPointerException) {
            if (exception.getMessage().contains(Authentication.class.getName())) {
                message = new ResponseMessage(TranslateUtil.getMessage(CommonMessages.FAILED_AUTHENTICATION, language), ResponseMessageStatus.ERROR);
            } else {
                message = new ResponseMessage(TranslateUtil.getMessage(CommonMessages.NULL_POINTER, language), ResponseMessageStatus.ERROR);
            }
        } else if (exception instanceof NoResourceFoundException) {
            message = new ResponseMessage(TranslateUtil.getMessage(CommonMessages.NO_RESOURCES_FOUND, language), ResponseMessageStatus.ERROR);
        } else if (exception instanceof HttpMessageNotReadableException) {
            message = new ResponseMessage(TranslateUtil.getMessage(CommonMessages.CANNOT_PARSE_DATA, language), ResponseMessageStatus.ERROR);
        } else if (exception instanceof MissingRequestHeaderException) {
            message = new ResponseMessage(TranslateUtil.getMessage(CommonMessages.MISSING_REQUEST_HEADER, language), ResponseMessageStatus.ERROR);
        } else if (exception instanceof UsernameNotFoundException) {
            message = new ResponseMessage(TranslateUtil.getMessage(CommonMessages.USER_NOT_FOUND, language), ResponseMessageStatus.ERROR);
        } else if (exception instanceof InternalAuthenticationServiceException) {
            message = new ResponseMessage(TranslateUtil.getMessage(CommonMessages.USER_IS_NOT_EXIST, language), ResponseMessageStatus.ERROR);
        } else if (exception instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
            List<String> fieldErrors = new ArrayList<>();
            methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(fieldError -> fieldErrors.add(TranslateUtil.getMessage(fieldError.getDefaultMessage(), language)));
            message = new ResponseMessage(StringUtil.join(fieldErrors, "\n"), ResponseMessageStatus.ERROR);
        } else if (exception instanceof MethodArgumentTypeMismatchException) {
            message = new ResponseMessage(TranslateUtil.getMessage(CommonMessages.LANGUAGE_IS_NOT_EXIST, language), ResponseMessageStatus.ERROR);
        } else if (exception instanceof NoSuchElementException) {
            message = new ResponseMessage(TranslateUtil.getMessage(CommonMessages.USER_NOT_FOUND, language), ResponseMessageStatus.ERROR);
        } else {
            message = new ResponseMessage(TranslateUtil.getMessage(CommonMessages.ERROR, language), ResponseMessageStatus.ERROR);
        }
        return ResponseEntity.badRequest().body(Response.getResponse("message", message));
    }

}
