package com.sparta.tigercave.config;

import com.sparta.tigercave.entity.UserRoleEnum;
import com.sparta.tigercave.jwt.JwtAuthFilter;
import com.sparta.tigercave.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {
    private final JwtUtil jwtUtil;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);    //세션을 사용하지 않는다고 설정한다.

        http.authorizeRequests()
                .antMatchers("/api/user/signup").permitAll()   //해당 url은 필터를 거치지 않도록 설정 수정 permitAll은
                .antMatchers("/api/user/login").permitAll()   //해당 url은 필터를 거치지 않도록 설정
                .antMatchers("/api/**").hasAnyRole(UserRoleEnum.ADMIN.name(), UserRoleEnum.USER.name())      //ADMIN, USER권한은 JWT필터를 거치도록 함
//                .antMatchers("/api/**").hasRole("USER")
                .anyRequest().authenticated()
                .and().addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);


        http.formLogin().loginProcessingUrl("/api/user/");
//                loginPage("/api/user/login?error") // 이상해씨 login-page 로그인 메소드 요청 구분해서
//                .failureUrl("/")
//                .permitAll();
        return http.build();
    }
}
