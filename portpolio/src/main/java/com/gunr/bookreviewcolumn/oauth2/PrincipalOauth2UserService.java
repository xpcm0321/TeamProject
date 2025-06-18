package com.gunr.bookreviewcolumn.oauth2;

import com.gunr.bookreviewcolumn.image.Image;
import com.gunr.bookreviewcolumn.image.ImageRepository;
import com.gunr.bookreviewcolumn.member.Member;
import com.gunr.bookreviewcolumn.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageRepository imageRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        
        String provider = userRequest.getClientRegistration().getRegistrationId(); // google, kakao, naver
        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuth2UserInfo userInfo = getOAuth2UserInfo(provider, attributes);

        String providerId = userInfo.getProviderId();
        String email = userInfo.getProviderEmail();
        String nickname = userInfo.getProviderName();
        String oauthId = provider + "_" + providerId;
        
        // 회원인지 확인
        Optional<Member> memberfind = memberRepository.findByOauthId(oauthId);
        Member member;
        
        if (memberfind.isPresent()) {
            member = memberfind.get();
        } else {
        	if (email != null && memberRepository.existsByEmail(email)) {
        		OAuth2Error error = new OAuth2Error("duplicate_email");
        	    throw new OAuth2AuthenticationException(error);
            }
        
	     // 3. username 생성 (기본은 email 앞부분 → 중복 있으면 번호 붙이기)
	     String baseUsername = (email != null) ? email.split("@")[0] : "user";
	     String username = baseUsername;
	     int count = 1;
	     
	     // 프로필 이미지 자동 생성
	     Image defaultImage = imageRepository.findByImg("/images/profile1.png");
	     if (defaultImage == null) {
	         throw new RuntimeException("기본 이미지가 DB에 없습니다.");
	     }
	     
	     while (memberRepository.existsByUsername(username)) {
             username = baseUsername + count++;
         }
            // 자동 회원가입
	     member = Member.builder()
                 .oauthId(oauthId)
                 .provider(provider)
                 .accessToken(userRequest.getAccessToken().getTokenValue())
                 .email(email != null ? email : oauthId + "@noemail.com")
                 .nickname(nickname != null ? nickname : "소셜유저")
                 .username(username)
                 .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                 .age(0)
                 .image(defaultImage) 
                 .memberimg(defaultImage.getImg())
                 .datetime(LocalDateTime.now())
                 .build();
	     memberRepository.save(member);
        }

        return new PrincipalDetails(member, Collections.singleton(new SimpleGrantedAuthority("ROLE_MEMBER")), attributes);
    }

    private OAuth2UserInfo getOAuth2UserInfo(String provider, Map<String, Object> attributes) {
        if ("google".equals(provider)) {
            return new GoogleUserInfo(attributes);
        } else if ("naver".equals(provider)) {
            return new NaverUserInfo((Map<String, Object>) attributes.get("response"));
        } else if ("kakao".equals(provider)) {
            return new KakaoUserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationException(provider + " 는 지원하지 않는 소셜 로그인입니다. ");
        }
    }
    
}

