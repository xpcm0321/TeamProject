package com.gunr.bookreviewcolumn.member;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.gunr.bookreviewcolumn.oauth2.PrincipalOauth2UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final PrincipalOauth2UserService principalOauth2UserService;

	public SecurityConfig(PrincipalOauth2UserService principalOauth2UserService) {
		this.principalOauth2UserService = principalOauth2UserService;
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		
		 http
		 	// .csrf().disable() // CSRF 비활성화(개발용) 나중에 제거 예정
		 	.authorizeHttpRequests( 
				 (authorizeHttpRequests) -> authorizeHttpRequests
				// admin만 접근가능
				.requestMatchers(new AntPathRequestMatcher("/admin/**"))
				.hasAuthority("ROLE_ADMIN") // ADMIN 역할
				// member만 접근가능
				.requestMatchers(new AntPathRequestMatcher("/member/mypage"))
				.hasRole("MEMBER")   // MEMBER 역할
				// 기타페이지 모두 접근가능(로그인 필요 없음)
				.requestMatchers(new AntPathRequestMatcher("/**"))
				.permitAll()
				 
				 				 
			).formLogin(
							(formLogin) -> formLogin
												.loginPage("/member/login")
												.defaultSuccessUrl("/board/main" , true) // ## 로그인시 넘어가는 화면
												.usernameParameter("username")
											    .passwordParameter("password")
			).logout(
							(logout) -> logout
											.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
											.logoutSuccessUrl("/member/login")
											.invalidateHttpSession(true)
											)
											
		 .oauth2Login(oauth2 -> oauth2
										 	.loginPage("/member/login")
								            .defaultSuccessUrl("/board/main", true)
								            .userInfoEndpoint(userInfo -> userInfo.userService(principalOauth2UserService))
								            .failureHandler((request, response, exception) -> {
								            	request.getSession().invalidate();
								            	if (exception instanceof OAuth2AuthenticationException) {
								                    String errorCode = ((OAuth2AuthenticationException) exception).getError().getErrorCode();
								                    if ("duplicate_email".equals(errorCode)) {
								                        response.sendRedirect("/member/login?emailError=true");
								                        return;
								                    }
								                }
								                response.sendRedirect("/member/login?error");
								            })
										);
		 return http.build();
	}
	
	@Bean
	AuthenticationManager authenticationManager(
			AuthenticationConfiguration authenticationConfiguration
	) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
}
