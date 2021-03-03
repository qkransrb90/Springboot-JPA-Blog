package com.cos.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@Service
public class PrincipalDetailService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	@Autowired
	public PrincipalDetailService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// username이 DB에 있는 값인지 확인
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User principal = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. [username: " + username + "]"));
		return new PrincipalDetail(principal);	// 시큐리티 세션에 유저 정보가 저장되는 시점!
	}
}
