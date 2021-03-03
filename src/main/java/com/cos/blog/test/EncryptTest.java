package com.cos.blog.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptTest {

	public static void main(String[] args) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = passwordEncoder.encode("1234");
		System.out.println(password);
	}
}
