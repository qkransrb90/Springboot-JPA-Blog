package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {
	
	private final UserService userService;
	
	@Autowired
	public UserApiController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
		user.setRole(RoleType.USER);		
		int result = userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.CREATED, result);
	}
	
	/* @PostMapping("/api/user/login")
	public ResponseDto<Integer> login(@RequestBody User user, HttpSession session) {
		User principal = userService.로그인(user);
		if (principal != null) {
			session.setAttribute("principal", principal);
		}
		return new ResponseDto<Integer>(HttpStatus.OK, 1);
	} */
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user, @AuthenticationPrincipal PrincipalDetail principal) {
		User updatedUser = userService.회원수정(user);
		principal.getUser().setPassword(updatedUser.getPassword());
		principal.getUser().setEmail(updatedUser.getEmail());
		return new ResponseDto<Integer>(HttpStatus.OK, 1);
	}
}
