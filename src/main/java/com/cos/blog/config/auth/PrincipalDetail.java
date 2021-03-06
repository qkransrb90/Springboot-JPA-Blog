package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.model.User;

import lombok.Getter;

public class PrincipalDetail implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	@Getter
	private User user;
	
	public PrincipalDetail(User user) {
		this.user = user;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	// 계정 만료 여부 (true: 만료되지 않았다.)
	@Override
	public boolean isAccountNonExpired() { 
		return true;
	}

	// 계정 잠김 여부 (true: 잠기지 않았다.)
	@Override
	public boolean isAccountNonLocked() { 
		return true;
	}

	// 비밀번호 만료 여부 (true: 만료되지 않았다.)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 계정 활성화 여부 (true: 활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	// 계정의 권한 목록을 반환
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		collectors.add(() -> "ROLE_" + user.getRole());
		return collectors;
	}
}
