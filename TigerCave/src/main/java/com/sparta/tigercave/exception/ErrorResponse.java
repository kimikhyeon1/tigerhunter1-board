package com.sparta.tigercave.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

//실제 유저가 보는 응답 형식
@Getter
@Builder
public class ErrorResponse {

    //HttpStatus 상태 코드
    private final int status;
    //HttpStatus 상태 코드
    private final String error;
    //ErrorCode에 설정한 에러코드
    private final String code;
    //ErrorCode에 설정한 메시지
    private final String message;

    //ResponseEntity는 Http 상태코드와 결과 데이터를 직접 제어할 수 있는 클래스
    //HttpEntity를 상속, HttpRequest에 대한 응답 데이터를 포함
    //Httpstatus : 응답 상태
    //HttpHeaders : 응답에 대한 추가 정보가 담김 (ex. body의 총 길이...)
    //HttpBody : 응답의 실제 내용이 담김

    //ErrorCode를 받아서 ResponseEntity<ErrorResponse> 형식으로 변환시켜주는 코드
    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getHttpStatus().value())
                        .error(errorCode.getHttpStatus().name())
                        .code(errorCode.name())
                        .message(errorCode.getMessage())
                        .build()
                );
    }
}
