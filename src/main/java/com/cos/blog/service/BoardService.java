package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;

@Service
public class BoardService {

	private final BoardRepository boardRepository;
	
	@Autowired
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}
	
	@Transactional
	public void 글쓰기(Board board, User user) {
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Board 글상세보기(Long id) {
		return boardRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 글 정보가 없습니다."));
	}
	
	@Transactional
	public void 글삭제(Long id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void 글수정(Long id, Board board) {
		Board findBoard = boardRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 글 정보가 없습니다."));
		findBoard.setTitle(board.getTitle());
		findBoard.setContent(board.getContent());
	}
}
