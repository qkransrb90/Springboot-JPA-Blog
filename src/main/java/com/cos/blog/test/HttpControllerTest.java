package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpControllerTest {

	// http://localhost:8080/test/get (select)
	@GetMapping("/http/get")
	public String getTest(Member m) {
		return "get-request";
	}
	
	// http://localhost:8080/test/post (insert)
	@PostMapping("/http/post")
	public String postTest() {
		return "post-request";
	}
	
	// http://localhost:8080/test/put (update)
	@PutMapping("/http/put")
	public String putTest() {
		return "put-request";
	}
	
	// http://localhost:8080/test/delete (delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete-request";
	}
}
