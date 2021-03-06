package com.cos.blog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Transactional
	public void 회원가입(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}
	
	/* @Transactional(readOnly = true) // select 시 transaction 시작, 서비스 종료 시 transaction 종료 (정합성 유지)
	public User 로그인(User user) {
		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	} */
	
	@Transactional
	public void 회원수정(User user) {
		User findUser = userRepository.findById(user.getId())
				.orElseThrow(() -> new IllegalArgumentException("해당 회원정보가 없습니다."));
		if (findUser.getOauth() == null) {
			findUser.setPassword(passwordEncoder.encode(user.getPassword()));
			findUser.setEmail(user.getEmail());
		}
	}
	
	@Transactional(readOnly = true)
	public User 회원찾기(String username) {
		Optional<User> findUser = userRepository.findByUsername(username);
		if (findUser.isPresent()) {
			return findUser.get();
		} else {
			return null;
		}
	}
}
