package kz.tempest.tpapp.commons.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.tempest.tpapp.commons.constants.ErrorMessages;
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

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(Exception.class)
    public static ResponseEntity<Response> handleExceptions (HttpServletRequest request, HttpServletResponse response, Exception exception) {
        Language language = (StringUtil.isNotEmpty(request.getHeader("Current-Language")) && Language.contains(request.getHeader("Current-Language"))) ?
                Language.valueOf(request.getHeader("Current-Language"))   :
                Language.ru;
        ResponseMessage message;
        if (exception instanceof AccessDeniedException) {
            message = new ResponseMessage(TranslateUtil.getMessage(ErrorMessages.ACCESS_DENIED, language), ResponseMessageStatus.ERROR);
        } else if (exception instanceof DisabledException) {
            message = new ResponseMessage(TranslateUtil.getMessage(ErrorMessages.USER_IS_DISABLED, language), ResponseMessageStatus.ERROR);
        } else if (exception instanceof BadCredentialsException) {
            message = new ResponseMessage(TranslateUtil.getMessage(ErrorMessages.USERNAME_OR_PASSWORD_IS_WRONG, language), ResponseMessageStatus.ERROR);
        } else if (exception instanceof MissingPathVariableException) {
            message = new ResponseMessage(TranslateUtil.getMessage(ErrorMessages.ENTITY_IS_NOT_FOUND, language), ResponseMessageStatus.ERROR);
        } else if (exception instanceof NullPointerException) {
            if (exception.getMessage().contains(Authentication.class.getName())) {
                message = new ResponseMessage(TranslateUtil.getMessage(ErrorMessages.FAILED_AUTHENTICATION, language), ResponseMessageStatus.ERROR);
            } else {
                message = new ResponseMessage(TranslateUtil.getMessage(ErrorMessages.NULL_POINTER, language), ResponseMessageStatus.ERROR);
            }
        } else if (exception instanceof NoResourceFoundException) {
            message = new ResponseMessage(TranslateUtil.getMessage(ErrorMessages.NO_RESOURCES_FOUND, language), ResponseMessageStatus.ERROR);
        } else if (exception instanceof HttpMessageNotReadableException) {
            message = new ResponseMessage(TranslateUtil.getMessage(ErrorMessages.CANNOT_PARSE_DATA, language), ResponseMessageStatus.ERROR);
        } else if (exception instanceof MissingRequestHeaderException) {
            message = new ResponseMessage(TranslateUtil.getMessage(ErrorMessages.MISSING_REQUEST_HEADER, language), ResponseMessageStatus.ERROR);
        } else if (exception instanceof UsernameNotFoundException) {
            message = new ResponseMessage(TranslateUtil.getMessage(ErrorMessages.USER_NOT_FOUND, language), ResponseMessageStatus.ERROR);
        } else if (exception instanceof InternalAuthenticationServiceException) {
            message = new ResponseMessage(TranslateUtil.getMessage(ErrorMessages.USER_IS_NOT_EXIST, language), ResponseMessageStatus.ERROR);
        } else if (exception instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
            List<String> fieldErrors = new ArrayList<>();
            methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(fieldError -> fieldErrors.add(TranslateUtil.getMessage(fieldError.getDefaultMessage(), language)));
            message = new ResponseMessage(StringUtil.join(fieldErrors, "\n"), ResponseMessageStatus.ERROR);
        } else if (exception instanceof MethodArgumentTypeMismatchException) {
            message = new ResponseMessage(TranslateUtil.getMessage(ErrorMessages.LANGUAGE_IS_NOT_EXIST, language), ResponseMessageStatus.ERROR);
        } else {
            message = new ResponseMessage(TranslateUtil.getMessage(ErrorMessages.ERROR, language), ResponseMessageStatus.ERROR);
        }
        return ResponseEntity.badRequest().body(Response.getResponse("message", message));
    }

}
