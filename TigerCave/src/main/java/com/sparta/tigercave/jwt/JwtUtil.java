package com.sparta.tigercave.jwt;

import com.sparta.tigercave.entity.UsersRoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Slf4j              // SLF4J는 Java의 로깅 모듈들의 추상체 -> 로그를 찍어주기 위함
@Component          //개발자가 직접 작성한 class를 bean으로 등록하려고 할때 사용하는 어노테이션
@RequiredArgsConstructor
public class JwtUtil {

    private String secretKey = "tigercavesecret";
    private static final long TOKEN_TIME = 60 * 60 * 1000L;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    //객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    //JWT토큰 생성
    public String createToken(String username, UsersRoleEnum role){

        Claims claims = Jwts.claims().setSubject(username);

        //jwt payload에 저장되는 정보 단위, 보통 여기서 user를 식별하는 값을 넣는다.
        claims.put("role", role);   //ket/value 값으로 저장됨
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)  //정보저장
                .setIssuedAt(now)   //토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + TOKEN_TIME))    //토큰 만료 시간
                .signWith(signatureAlgorithm, secretKey)    //사용할 암호화 알고리즘과 signature에 들어갈 secret값 세팅
                .compact();     //토큰 생성
    }

    //JWT토큰 유효성 검사 , 만료일자 확인
    public boolean validateToken(String jwtToken){
        try{
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        }catch (Exception e){
            return false;
        }
    }
}
