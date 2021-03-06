package com.cos.blog.dto;

import lombok.Data;

@Data
public class ReplySaveRequestDto {

	private Long userId;
	private Long boardId;
	private String content;
}
