const index = {
	
	init: function() {
		const _this = this;
		$("#btn-save").on("click", function(e) {
			e.preventDefault();
			_this.save();
		});
		
		$("#btn-update").on("click", function(e) {
			e.preventDefault();
			_this.update();
		});
	},
	
	save: function() {
		const data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		}
		
		$.ajax({
			type: "post",
			url: "/auth/joinProc",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		})
		.done(function(response) {
			if (response.status === "CREATED") {
				alert("회원가입이 완료되었습니다.");
				location.href = "/";
			} else {
				alert("회원가입에 실패하였습니다.");
				location.href = "/auth/joinForm";
			}
		})
		.fail(function(error) {
			alert(JSON.stringify(error));
		});
	},
	
	update: function() {
		const data = {
			id: $("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		}
		
		$.ajax({
			type: "put",
			url: "/user",
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