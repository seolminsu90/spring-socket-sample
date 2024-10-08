### 웹 소켓 연결 예제 프로젝트 (2023-12)
- Spring boot 3.2 + Openjdk17
- Spring security + REST API
- Stomp + websocket sample
- Simple jpa / querydsl + h2

#### 기본적인 클라이언트   

socket connect 시에 headers로 인증하여 사용한다.
자세한건 **client.html**을 열어보면 된다.

```javascript
const socket = () => new SockJS('http://localhost:9000/ws'); // 일반 소켓 연결은 /ws/websocket을 이용
let options = {debug: true};
const stompClient = Stomp.over(socket, options); // 함수형으로 제공해줘야 재연결을 자동으로 해줌

let headers = {Authorization: "my tokens"}; // 연결 인증

stompClient.connect(headers, (frame) => {
    console.log('소켓 연결 성공', frame);
    stompClient.subscribe('/topic/sample-topic', (tick) => {
        console.log("수신됬습니다.", tick.body);
    })

    // 서버/클라의 content-type 이 맞아야한다?
    stompClient.send('/pub/send', {'content-type': 'text/plain'}, 'Hello, Stomp!');
}, (error) => {
    console.log('연결실패');
    console.log(error)
})
```

#### 메시지 전달 시 의 처리

```json
// 페이로드 자체에서 처리하거나, 연결 시와 동일하게 헤더를 전달한다.
{
  "sender": "userA",
  "message": "hello world"
}

```

#### 기능 지원

- 토픽별 권한, 유저 자동 매핑 등등 기능이 많은데 그냥 최대한 로우레벨에서 처리해도 무방할 것 같음
- 토픽별 권한은 캐싱된 권한 테이블 정보로 send시 destination과 authorization header role과 비교해서 처리할 수있을 것 같음
- 유저 정보는 authorization header로 충분
- 구독 시 권한체크 등을 통해 message channel을 구분해도 될 것 같다.
- 기타 등등 처리는 그때그떄..