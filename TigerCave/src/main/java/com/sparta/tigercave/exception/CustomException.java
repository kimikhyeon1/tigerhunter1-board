package com.sparta.tigercave.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 프로그램 전역으로 사용할 CustomException
// RuntimeException을 상속해 프로그램 실행 중 발생하는 예외를 처리
// 생성자로 ErrorCode를 받음
@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{
    private final ErrorCode errorCode;
}