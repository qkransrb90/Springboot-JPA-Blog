<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../layout/header.jsp"%>

<div class="container">
	<button id="btn-list" class="btn btn-secondary">뒤로가기</button>
	<c:if test="${board.user.id eq principal.user.id}">
		<button class="btn btn-warning" onclick="location.href='/board/<c:out value="${board.id}" />/updateForm'">수정</button>
		<button id="btn-delete" class="btn btn-danger">삭제</button>
	</c:if>
	<br/>
	<br/>
	<div>
		번호: <span id="id"><i>${board.id}</i></span>&nbsp;&nbsp;
		작성자: <span><i>${board.user.username}</i></span>
	</div>
	<br/>
	<div class="form-group">
		<h3>${board.title}</h3>
	</div>
	<hr/>
	<div class="form-group">
		<div>${board.content}</div>
	</div>
	<div class="card">
		<div class="card-body">
			<textarea id="reply-content" class="form-control" rows="1"></textarea>
		</div>
		<div class="card-footer">
			<button id="btn-reply-save" class="btn btn-primary">등록</button>
		</div>
	</div>
	<form>
		<input type="hidden" id="userId" value="${principal.user.id}" />
		<input type="hidden" id="boardId" value="${board.id}" />
		<input type="hidden" id="userId" value="${principal.user.id}" />
	</form>
	<br/>
	<div class="card">
		<div class="card-header">댓글</div>
		<ul id="reply-box" class="list-group">
			<c:forEach items="${board.replys}" var="reply">
				<li id="reply-${reply.id}" class="list-group-item d-flex justify-content-between">
			  		<div><c:out value="${reply.content}" /></div>
			  		<div class="d-flex">
			  			<div class="font-italic">작성자:&nbsp;<c:out value="${reply.user.username}" />&nbsp;&nbsp;</div>
			  			<c:if test="${principal.user.id eq reply.user.id}">
			  				<button class="badge" onclick="index.replyDelete(${board.id}, ${reply.id})">삭제</button>
			  			</c:if>
			  		</div>
			  	</li>
			</c:forEach>
		</ul>
	</div>
</div>
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>