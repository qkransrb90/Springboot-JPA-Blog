package com.cos.blog.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OauthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * /auth/** -> 인증이 안된 사용자들이 출입할 수 있는 허용 경로
 * 주소가 / 이면, index.jsp 허용
 * /static 이하에 있는 파일들 허용
 */

@Controller
public class UserController {
	
	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	
	@Autowired
	public UserController(UserService userService, AuthenticationManager authenticationManager) {
		this.userService = userService;
		this.authenticationManager = authenticationManager;
	}
	
	@Value("${cos.key}")
	private String cosKey;

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}
	
	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(@RequestParam(required = true) String code) throws Exception {
		// RestTemplate
		RestTemplate restTemplate = new RestTemplate();
		
		// header setting
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// body setting
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "f01c561ba1b73a7f4676aa4f93210f49");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);
		
		// entity(body + header) setting
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = 
				new HttpEntity<MultiValueMap<String,String>>(params, headers);
		
		// response data
		ResponseEntity<String> response = restTemplate.exchange(
				"https://kauth.kakao.com/oauth/token", HttpMethod.POST, kakaoTokenRequest, String.class);
		
		OauthToken oauthToken = new OauthToken();
		
		ObjectMapper objectMapper = new ObjectMapper();
		oauthToken = objectMapper.readValue(response.getBody(), OauthToken.class);
		
		// get kakao-login user information
		headers.add("Authorization", "Bearer " + oauthToken.getAccess_token());
		
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
		
		ResponseEntity<String> response2 = restTemplate.exchange(
				"https://kapi.kakao.com/v2/user/me", HttpMethod.POST, entity, String.class);			
		
		KakaoProfile kakaoProfile = new KakaoProfile();
		kakaoProfile = objectMapper.readValue(response2.getBody(), KakaoProfile.class);
		
		System.out.println("#kakaoProfile id: " + kakaoProfile.getId());
		System.out.println("#kakaoProfile username: " + kakaoProfile.getKakao_account().getProfile().getNickname());
		System.out.println("#kakaoProfile email: " + kakaoProfile.getKakao_account().getEmail());
		
		UUID tempPassword = UUID.randomUUID();
		System.out.println("blog-kakao-username: " + "kakao_" + kakaoProfile.getId());
		System.out.println("blog-kakao-email: " + kakaoProfile.getKakao_account().getEmail());
		System.out.println("blog-kakao-password: " + tempPassword);
		
		User user = User.builder()
				.username("kakao_" + kakaoProfile.getId())
				.email(kakaoProfile.getKakao_account().getEmail())
				.oauth("kakao")		
				.password(cosKey)
				.build();
		
		User findUser = userService.회원찾기(user.getUsername());
		
		if (findUser == null) {
			userService.회원가입(user);
			System.out.println("회원 가입을 축하합니다...");
			
		} else {
			System.out.println("기존 가입 사용자...");
		}
		
		System.out.println("#로그인 처리 시작!");
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		System.out.println("#로그인 처리 끝!");
		
		return "redirect:/";
	}
}
