# 실습을 위한 개발 환경 세팅
* https://github.com/slipp/web-application-server 프로젝트를 자신의 계정으로 Fork한다. Github 우측 상단의 Fork 버튼을 클릭하면 자신의 계정으로 Fork된다.
* Fork한 프로젝트를 eclipse 또는 터미널에서 clone 한다.
* Fork한 프로젝트를 eclipse로 import한 후에 Maven 빌드 도구를 활용해 eclipse 프로젝트로 변환한다.(mvn eclipse:clean eclipse:eclipse)
* 빌드가 성공하면 반드시 refresh(fn + f5)를 실행해야 한다.

# 웹 서버 시작 및 테스트
* webserver.WebServer 는 사용자의 요청을 받아 RequestHandler에 작업을 위임하는 클래스이다.
* 사용자 요청에 대한 모든 처리는 RequestHandler 클래스의 run() 메서드가 담당한다.
* WebServer를 실행한 후 브라우저에서 http://localhost:8080으로 접속해 "Hello World" 메시지가 출력되는지 확인한다.

# 각 요구사항별 학습 내용 정리
* 구현 단계에서는 각 요구사항을 구현하는데 집중한다. 
* 구현을 완료한 후 구현 과정에서 새롭게 알게된 내용, 궁금한 내용을 기록한다.
* 각 요구사항을 구현하는 것이 중요한 것이 아니라 구현 과정을 통해 학습한 내용을 인식하는 것이 배움에 중요하다. 

### 요구사항 1 - http://localhost:8080/index.html로 접속시 응답

- 상대경로로 파일을 가져올 때 Java에서의 기준이되는 위치는 작업중인 프로젝트 폴더
- ex 내 예시에선 web-application-server-master
  
| 입력스트림                | 출력스트림                 | 입출력대상 종류    |
|----------------------|-----------------------|-------------|
| FileInputStream      | FileOutputStream      | 파일          |
| ByteArrayInputStream | ByteArrayOutputStream | 메모리(byte배열) |
| PipedInputStream     | PipedOutputStream     | 프로세스        |
| AudioInputStream     | AudioOutputStream     | 오디오장치       |

| InputStream                           | OuputStream                             |
|---------------------------------------|-----------------------------------------|
| abstract int read()                   | abstract void write(int b)              |
 | int read(byte [] b)                   | void write (byte [] b)                  |
 | int read(byte {} b, int off, int len) | void write(byte {} b, int off, int len) |

> 위에 read()의 반환타입이 byte가 아니라 int 인 이유는 read()의 반환값 범위가 0~255와 -1이기 때문이다.

### InputStream관련 메소드

|||
|------|----|
|read()|입력 스트림으로부터 1바이트를 읽어서 바이트를 리턴|
|read(byte[] b)|입력 스트림으로부터 읽은 바이트들을 매개값으로 주어진 바이트 배열 b 에 저장하고 실제로 읽은 바이트 수를 리턴|
|read(byte[] b, int off, int len)|입력 스트림으로부터 len 개의 바이트만큼 읽고 매개값으로 주어진 바이트 배열 b[off] 부터 len 개까지 저장. 그리고 실제로 읽은 바이트 수인 len 개를 리턴. 만약 len 개를 모두 읽지 못하면 실제로 읽은 바이트 수를 리턴|
|close()|사용한 시스템 자원을 반납하고 입력 스트림 닫기|

### OutputStream관련 메소드

|||
|---|---|
|write(int b)|출력 스트림으로부터 1바이트를 보낸다.(b 의 끝 1바이트|
|write(byte[] b)|출력 스트림으로부터 주어진 바이트 배열 b의 모든 바이트를 보낸다.|
|write(byte[ ] b, int off, int len)|출력 스트림으로 주어진 바이트 배열 b[off] 부터 len 개까지의 바이트를 보낸다.|
|flush()|버퍼에 잔류하는 모든 바이트를 출력한다.|
|close()|사용한 시스템 자원을 반납하고 입력 스트림 닫기|


### 요구사항 2 - get 방식으로 회원가입
* 

### 요구사항 3 - post 방식으로 회원가입
* 

### 요구사항 4 - redirect 방식으로 이동
* 

### 요구사항 5 - cookie
* 

### 요구사항 6 - stylesheet 적용
* Accept의 내용을 보고 

### heroku 서버에 배포 후


## 참고

- [로깅 라이브러리](https://www.youtube.com/watch?v=040Y3MBNnyw&ab_channel=%EB%B0%95%EC%9E%AC%EC%84%B1)