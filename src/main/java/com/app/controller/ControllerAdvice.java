package com.app.controller;

import com.app.exception.MyException;
import com.app.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.NotAcceptableStatusException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseResponse handleNotFoundException(IllegalArgumentException exception) {

        return BaseResponse.createErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage() );
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse handleBadRequestException(MyException exception) {

        return BaseResponse.createErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(value = {NotAcceptableStatusException.class})
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public BaseResponse handleNotAcceptException(NotAcceptableStatusException exception) {

        return BaseResponse.createErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), exception.getReason());
    }

    @ExceptionHandler(value = {AuthorizationServiceException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseResponse handleUnAuthorizeException(AuthorizationServiceException exception) {

        return BaseResponse.createErrorResponse(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
    }
}
