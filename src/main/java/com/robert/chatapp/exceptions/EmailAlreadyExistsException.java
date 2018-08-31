package com.robert.chatapp.exceptions;

import com.robert.chatapp.dto.ErrorDto;
import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends RuntimeException {

    private ErrorDto errorDto;

    public EmailAlreadyExistsException(String message) {
        super(message);
        errorDto = new ErrorDto();
        errorDto.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        errorDto.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDto.setMessage(message);
    }

    public EmailAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public ErrorDto getErrorDto() {
        return errorDto;
    }

    public void setErrorDto(ErrorDto errorDto) {
        this.errorDto = errorDto;
    }
}
