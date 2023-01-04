package com.sparta.tigercave.jwt;


import com.sparta.tigercave.dto.AuthenticatedUserInfoDto;
import com.sparta.tigercave.entity.UserRoleEnum;
import com.sparta.tigercave.exception.CustomException;
import com.sparta.tigercave.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import static com.sparta.tigercave.exception.ErrorCode.INVALID_TOKEN;

@Component          //개발자가 직접 작성한 class를 bean으로 등록하려고 할때 사용하는 어노테이션
@RequiredArgsConstructor
public class JwtUtil {

    private final UserDetailsService userDetailsService;

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    @Value("${jwt.secret.key}")
    private String secretKey;
    private static final long TOKEN_TIME = 60 * 60 * 1000L;

    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    //JWT토큰 생성
    public String createToken(String username, UserRoleEnum role){

        Claims claims = Jwts.claims().setSubject(username);

        //jwt payload에 저장되는 정보 단위, 보통 여기서 user를 식별하는 값을 넣는다.
        claims.put("role", role);   //ket/value 값으로 저장됨
        Date now = new Date();
        return BEARER_PREFIX+
                Jwts.builder()
                        .setClaims(claims)  //정보저장
                        .setIssuedAt(now)   //토큰 발행 시간 정보
                        .setExpiration(new Date(now.getTime() + TOKEN_TIME))    //토큰 만료 시간
                        .signWith(signatureAlgorithm, key)    //사용할 암호화 알고리즘과 signature에 들어갈 secret값 세팅
                        .compact();     //토큰 생성
    }

    //JWT토큰 유효성 검사 , 만료일자 확인
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            new CustomException(INVALID_TOKEN);
        }
        return false;
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        System.out.println(Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody());
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    //header 토큰 가져오기
    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    //인증객체 생성
    public Authentication createAuthentication(String username){
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}