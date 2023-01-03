package com.sparta.tigercave.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Timestamped {
    @CreatedDate
    private LocalDateTime createAt; // String -> LocalDateTime 으로 타입 수정 했습니다.
    @LastModifiedDate
    private LocalDateTime modifiedAt; // String -> LocalDateTime 으로 타입 수정 했습니다.
}
