<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	function addItem() {
		let todo = document.getElementById("item");
		// 할 일 칸에 입력한 내용을 가져와서 todo 변수에 대입
		let list = document.getElementById("todolist");
		// 기존의 할일 목록이 있으면 가져와서 list 변수에 대입
		let listitem = document.createElement("li");
		// 새로운 li 요소를 생성하여 listitem 변수에 대입

		listitem.className = "d-flex list-group-item list-group-item-action list-group-item-warning";

		listitem.innerText = todo.value;
		// 입력된 할일을 li 태그로 바디에 추가

		list.appendChild(listitem);
		// ul 요소의 하위요소로 li 요소 추가

		todo.value = "";
		todo.focus();
	}
</script>

<meta name="wiewport" content="width-device-width, initial-scale=1.0"/>

</head>
<body>
	<!-- CSS only -->
	<link
		href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
		rel="stylesheet"
		integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
		crossorigin="anonymous">

	<div class="container bg-warning shadow mx-auto mt-5 p-5 w-75">
		<h2>My Todo App</h2>
		<hr>
		<div class="input-group">
			<input id="item" class="form-control" type="text"
				placeholder="할일을 입력하세요.">
			<button type="button" class="btn btn-primary" onclick="addItem()">할일
				추가</button>
			<hr>
		</div>
			<ul id="todolist"></ul>
		
	</div>

	<!-- JavaScript Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa"
		crossorigin="anonymous"></script>
</body>
</html>