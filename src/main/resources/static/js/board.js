const index = {
	
	init: function() {
		const _this = this;
		$("#btn-save").on("click", function(e) {
			e.preventDefault();
			_this.save();
		});
		
		$("#btn-list").on("click", function() {
			history.back();
		});
		
		$("#btn-delete").on("click", function() {
			_this.remove();
		});
		
		$("#btn-update").on("click", function() {
			_this.update();
		});
	},
	
	save: function() {
		const data = {
			title: $("#title").val(),
			content: $("#content").val()
		}
		
		$.ajax({
			type: "post",
			url: "/api/board",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		})
		.done(function(response) {
			if (response.data === 1) {
				alert("글쓰기가 완료되었습니다.");
				location.href = "/";
			}
		})
		.fail(function(error) {
			alert(JSON.stringify(error));
		});
	},
	
	remove: function() {
		const id = $("#id").children().text().trim();
		$.ajax({
			type: "delete",
			url: "/api/board/" + id,
			dataType: "json"
		})
		.done(function() {
			alert("삭제가 완료되었습니다.");
			location.href = "/";
		})
		.fail(function(error) {
			alert(JSON.stringify(error));
		});
	},
	
	update: function() {
		const data = {
			id: $("#id").val(),
			title: $("#title").val(),
			content: $("#content").val()
		}
		
		$.ajax({
			type: "put",
			url: "/api/board/" + $("#id").val(),
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		})
		.done(function() {
			alert("수정이 완료되었습니다.");
			location.href = "/";
		})
		.fail(function(error) {
			alert(JSON.stringify(error));
		});
	}
}

index.init();