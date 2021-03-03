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
</div>
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>