package com.sparta.tigercave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling //스케줄링 기능을 사용하기 위한 설정
@EnableJpaAuditing // Timestamped를 사용하기 위한 설정
@SpringBootApplication
public class TigerCaveApplication {

    public static void main(String[] args) {
        SpringApplication.run(TigerCaveApplication.class, args);
    }

}
