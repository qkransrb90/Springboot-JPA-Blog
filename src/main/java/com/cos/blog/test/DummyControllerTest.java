package com.cos.blog.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {
	
	private UserRepository userRepository;
	
	@Autowired
	DummyControllerTest(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll();
	}
	
	@GetMapping("/dummy/user")
	public Page<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		return userRepository.findAll(pageable);
	}
	
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 사용자는 없습니다. id: " + id));
	}

	@PostMapping("/dummy/join")
	public String join(@ModelAttribute User user) {
		System.out.println("username: " + user.getUsername());
		System.out.println("password: " + user.getPassword());
		System.out.println("email: " + user.getEmail());
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
	
	@Transactional // 변경된 데이터에 대한 dirty-checking(변경 감지)을 통한 update 실행
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable Long id, @RequestBody User user) {
		User findUser = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("수정에 실패하였습니다."));
		findUser.setPassword(user.getPassword());
		findUser.setEmail(user.getEmail());
		return findUser;
	}
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable Long id) {
		try {
			userRepository.deleteById(id);
		} catch (Exception e) {
			return "삭제에 실패하였습니다. id: " + id;
		}
		return "삭제에 성공하였습니다. id: " + id;
	}
}
