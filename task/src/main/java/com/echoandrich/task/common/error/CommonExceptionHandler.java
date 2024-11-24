package com.echoandrich.task.common.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {

      log.error(e.getMessage(), e);

      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("서버 에러 발생");
    }
}
