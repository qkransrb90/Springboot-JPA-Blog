package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;

@Service
public class BoardService {

	private final BoardRepository boardRepository;
	private final ReplyRepository replyRepository;
	
	@Autowired
	public BoardService(BoardRepository boardRepository, ReplyRepository replyRepository) {
		this.boardRepository = boardRepository;
		this.replyRepository = replyRepository;
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
	
	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
		/* User user = userRepository.findById(replySaveRequestDto.getUserId())
				.orElseThrow(() -> new IllegalArgumentException("User 정보를 찾을 수 없습니다."));
		Board board = boardRepository.findById(replySaveRequestDto.getBoardId())
				.orElseThrow(() -> new IllegalArgumentException("Board 정보를 찾을 수 없습니다."));
		Reply reply = Reply.builder()
				.user(user)
				.board(board)
				.content(replySaveRequestDto.getContent())
				.build();
		replyRepository.save(reply); */
		
		replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
	}
	
	@Transactional
	public void 댓글삭제(Long replyId) {
		replyRepository.deleteById(replyId);
	}
}
