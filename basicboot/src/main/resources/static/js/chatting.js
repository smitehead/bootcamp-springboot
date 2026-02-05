const serverUrl="localhost:6789/chatting";
/*const serverUrl="13.129.12.44:8080/chatting';*/

//접속 url주소
// http -> ws://서버주소:포트/서비스주소
// https -> wss://서버주소:포트/서비스주소
const server=new WebSocket(`ws://${serverUrl}`);

server.onopen=response=>{
	//서버와 접속이 완료된 후 실행
	console.log(response);
	//서버에 데이터를 전송
	server.send(userId);
}

server.onmessage=response=>{
	//서버에서 send()했을때 실행
	//response.data속성에 서버에서 보낸 데이터가 저장되어있음
	console.log(response.data);
	const $h5=document.createElement("h5");
	$h5.innerText=response.data;
	document.getElementById("chat-container").appendChild($h5);
}









