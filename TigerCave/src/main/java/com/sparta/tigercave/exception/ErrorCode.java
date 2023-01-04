package com.sparta.tigercave.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

// 예외 형식을 Enum 클래스로 정의
// 모든 예외 케이스를 관리
// 기존의 HttpStatus, 커스텀한 예외, 메시지로 구성
@Getter
@AllArgsConstructor
public enum ErrorCode {
    //400 BAD_REQUEST 잘못된 요청
    INVALID_TOKEN(BAD_REQUEST, "토큰이 유효하지 않습니다."),
    INVALID_USER(BAD_REQUEST, "작성자만 삭제/수정할 수 있습니다."),
    ALREADY_LIKE_USER(BAD_REQUEST, "이미 좋아요한 사용자입니다."),
    NOT_YET_LIKE_USER(BAD_REQUEST, "아직 좋아요를 하지 않은 사용자입니다."),

    //401 UNAUTHORIZED 인증되지 않은 사용자
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다."),

    //404 NOT_FOUND 잘못된 리소스 접근
    USER_NOT_FOUND(NOT_FOUND,  "회원을 찾을 수 없습니다."),
    PASSWORD_NOT_FOUND(NOT_FOUND, "비밀번호가 일치하지 않습니다."),
    POST_NOT_FOUND(NOT_FOUND, "게시글이 존재하지 않습니다."),
    COMMENT_NOT_FOUND(NOT_FOUND, "댓글이 존재하지 않습니다."),
    ADMIN_PASSWORD_NOT_FOUND(NOT_FOUND, "관리자 암호가 일치하지 않아 등록이 불가합니다."),


    //409 CONFLICT 중복된 리소스
    DUPLICATE_USERNAME(CONFLICT, "중복된 username 입니다."),

    //500
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버 에러입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
