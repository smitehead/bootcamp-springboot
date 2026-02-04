<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jsp 화면</title>
</head>
<body>
	<h2>BOOT jsp화면</h2>
	<button id="btn">domo저장</button>
	<script>
		btn.addEventListener("click",async e=>{
				const demo={
						"devName":"강씨",
						"devAge":20,
						"devGender":'M',
						"devLang":["C","Java"],
						"devEmail":"1234"
				}
				const response=await fetch("/demo",{
					method:"post",
					headers:{
						"Content-Type":"application/json",
					},
					body:JSON.stringify(demo)
				})
				if(response.ok) {
					const data= await response.json();
					console.log(data);
				}
		})
	</script>
</body>
</html>




