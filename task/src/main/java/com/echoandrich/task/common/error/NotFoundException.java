package com.echoandrich.task.common.error;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotFoundException extends RuntimeException{

    private final String errorMessage;

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
