package com.sparta.tigercave.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//로깅 : 프로그램 동작시 발생하는 모든 일을 기록하는 것 (서비스 동작 상태, 장애)
//@Slf4j : 로깅 구현을 위해 추가
//@RestControllerAdvice : 모든 rest controller에서 발생하는 exception 처리
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //@ExceptionHandler : 발생한 exception에 대해 처리하는 메소드로 handling
    //메소드에 선언해 예외 처리를 하려는 클래스럴 지정하면, 예외 발생 시 정의된 로직으로 처리
    //런타임시 발생되는 모든 예외는 CustomException으로 처리
    @ExceptionHandler({ CustomException.class })
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("handleCustomException", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    //서버에러 별도 처리
    @ExceptionHandler({ Exception.class })
    protected ResponseEntity<ErrorResponse> handleServerException(CustomException e) {
        log.error("handleServerException", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}
