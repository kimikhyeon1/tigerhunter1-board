package com.sparta.tigercave.jwt;

import com.sparta.tigercave.exception.CustomException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.sparta.tigercave.exception.ErrorCode.INVALID_AUTH_TOKEN;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //헤더에서 jwt를 받아오기
        String token = jwtUtil.resolveToken(request);

        //유효한 토큰인지 확인
        if(token != null){
            if(!jwtUtil.validateToken(token)){
                new CustomException(INVALID_AUTH_TOKEN);
                return;
            }
            //토큰이 유효하면 토큰으로부터 유저정보를 받아오기
            Claims claims = jwtUtil.getUserInfoFromToken(token);
            //SecurityContext에 Authentication 객체를 저장.
            setAuthentication(claims.getSubject());
        }
        filterChain.doFilter(request, response);
    }

    public void setAuthentication(String username){
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }
}
