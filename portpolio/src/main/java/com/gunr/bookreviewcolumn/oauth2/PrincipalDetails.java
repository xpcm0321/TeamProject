package com.gunr.bookreviewcolumn.oauth2;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.gunr.bookreviewcolumn.member.Member;

public class PrincipalDetails implements OAuth2User, UserDetails {

	private static final long serialVersionUID = 1L;
	
	private final Member member;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Map<String, Object> attributes;

    public PrincipalDetails(Member member, Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes) {
        this.member = member;
        this.authorities = authorities;
        this.attributes = attributes;
    }

    @Override public Map<String, Object> getAttributes() { return attributes; }
    @Override public String getName() { return member.getUsername(); }
    @Override public String getUsername() { return member.getUsername(); }
    @Override public String getPassword() { return member.getPassword(); }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    public Member getMember() {
        return member;
    }
}


