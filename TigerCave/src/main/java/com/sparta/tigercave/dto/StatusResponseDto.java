package com.sparta.tigercave.dto;

import com.sparta.tigercave.entity.StatusEnum;
import lombok.Getter;

@Getter
public class StatusResponseDto {
    private StatusEnum status;
    private String message;

    public StatusResponseDto(StatusEnum statusEnum, String message) {
        this.status = statusEnum;
        this.message = message;
    }
}
