package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public Integer 회원가입(User user) {
		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepository.save(user);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserService 회원가입(): " + e.getMessage());
		}
		return -1;
	}
	
	/* @Transactional(readOnly = true) // select 시 transaction 시작, 서비스 종료 시 transaction 종료 (정합성 유지)
	public User 로그인(User user) {
		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	} */
	
	@Transactional
	public User 회원수정(User user) {
		User findUser = userRepository.findById(user.getId())
				.orElseThrow(() -> new IllegalArgumentException("해당 회원정보가 없습니다."));
		findUser.setPassword(passwordEncoder.encode(user.getPassword()));
		findUser.setEmail(user.getEmail());
		return findUser;
	}
}
