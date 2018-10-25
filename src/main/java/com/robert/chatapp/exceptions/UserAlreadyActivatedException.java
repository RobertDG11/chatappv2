package com.robert.chatapp.exceptions;

import com.robert.chatapp.dto.ErrorDto;
import org.springframework.http.HttpStatus;

public class UserAlreadyActivatedException extends RuntimeException{

    private ErrorDto errorDto;

    public UserAlreadyActivatedException(String message) {
        super(message);
        errorDto = new ErrorDto();
        errorDto.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        errorDto.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDto.setMessage(message);
    }

    public UserAlreadyActivatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyActivatedException(Throwable cause) {
        super(cause);
    }

    public ErrorDto getErrorDto() {
        return errorDto;
    }

    public void setErrorDto(ErrorDto errorDto) {
        this.errorDto = errorDto;
    }
}
