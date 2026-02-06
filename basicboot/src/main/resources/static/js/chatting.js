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
	server.send(new Message("open",userId,"","","").convert());
}

server.onmessage=response=>{
	//서버에서 send()했을때 실행
	//response.data속성에 서버에서 보낸 데이터가 저장되어있음
/*	console.log(response.data);
	const $h5=document.createElement("h5");
	$h5.innerText=response.data;
	document.getElementById("chat-container").appendChild($h5);*/
	const receiveData=Message.deconvert(response.data);
	switch(receiveData.type){
		case "open" : case "close" : alertMessage(receiveData); break;
		case "msg" : messagePrint(receiveData);break;
		case "attend" : addAttend(receiveData);break;
	}
}
function alertMessage(msg){
	const $container=document.createElement("div");
	$container.classList.add("alertContainer");
	const status=msg.type=='open'?"접속":"퇴장";
	const $h4=document.createElement("h4");
	$h4.innerText=`${msg.sender}님이 ${status}하셨습니다`;
	$container.appendChild($h4)
	document.getElementById("chat-container").appendChild($container);
}
function addAttend(msg){
	
}
function messagePrint(msg){
	const $div=document.createElement("div");
	const $sender=document.createElement("sup");
	$sender.innerText=msg.sender;
	const $content=document.createElement('span');
	$content.innerText=msg.data;
	$div.appendChild($sender);
	$div.appendChild($content);
	$div.classList.add("msgcontainer");
	if(msg.sender==userId){
		$div.classList.add("right");
	}else{
		$div.classList.add("left");
	}
	document.getElementById("chat-container").appendChild($div);
	
	
}


class Message{
	constructor(type="",sender="",receiver="",data="",room=""){
		this.type=type;
		this.sender=sender;
		this.receiver=receiver;
		this.data=data;
		this.room=room;
	}
	convert(){
		return JSON.stringify(this);
	}
	static deconvert(data){
		return JSON.parse(data);
	}
	
}







