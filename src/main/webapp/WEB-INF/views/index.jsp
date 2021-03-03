<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="layout/header.jsp"%>

<div class="container">
	<c:forEach items="${boards.content}" var="board">
		<div class="card m-2">
			<div class="card-body">
				<h4 class="card-title"><c:out value="${board.title}" /></h4>
				<a href="/board/${board.id}" class="btn btn-primary">상세보기</a>
			</div>
		</div>
	</c:forEach>
	<ul class="pagination justify-content-center">
		<c:choose>
			<c:when test="${boards.first eq true}">
				<li class="page-item disabled"><a class="page-link" href="#">Previous</a></li>
					<c:forEach var="i" begin="1" end="${boards.totalPages}">
						<li class="page-item"><a class="page-link" href="?page=${i - 1}">${i}</a></li>
					</c:forEach>
				<li class="page-item"><a class="page-link" href="?page=${boards.number + 1}">Next</a></li>
			</c:when>
			<c:when test="${boards.last eq true}">
				<li class="page-item"><a class="page-link" href="?page=${boards.number - 1}">Previous</a></li>
					<c:forEach var="i" begin="1" end="${boards.totalPages}">
						<li class="page-item"><a class="page-link" href="?page=${i - 1}">${i}</a></li>
					</c:forEach>
	  			<li class="page-item disabled"><a class="page-link" href="#">Next</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="?page=${boards.number - 1}">Previous</a></li>
					<c:forEach var="i" begin="1" end="${boards.totalPages}">
						<li class="page-item"><a class="page-link" href="?page=${i - 1}">${i}</a></li>
					</c:forEach>
	  			<li class="page-item"><a class="page-link" href="?page=${boards.number + 1}">Next</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
</div>

<%@ include file="layout/footer.jsp"%>