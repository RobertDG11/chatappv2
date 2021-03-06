package com.robert.chatapp.exceptions;

import com.robert.chatapp.dto.ErrorDto;
import org.springframework.http.HttpStatus;

public class GroupNotFoundException extends RuntimeException {

    private ErrorDto errorDto;

    public GroupNotFoundException(String message) {
        super(message);
        errorDto = new ErrorDto();
        errorDto.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        errorDto.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDto.setMessage(message);
    }

    public GroupNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public GroupNotFoundException(Throwable cause) {
        super(cause);
    }

    public ErrorDto getErrorDto() {
        return errorDto;
    }

    public void setErrorDto(ErrorDto errorDto) {
        this.errorDto = errorDto;
    }
}
