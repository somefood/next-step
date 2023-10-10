# 배운점

- DispatcherServlet을 등록할 때 urlPatterns를 "/"가 아니라 "/*"로 지정하면 JSP에 대한 요청 또한 여기에 연결되어 정상적으로 처리되지 않는다
- "/" 매핑은 매핑되어 있는 서블릿, JSP 요청이 아닌 자바스크립트, CSS, 이미지와 같은 요청을 처리하도록 설계되어있음
- 톰캣 서버의 기본 설정이 "/"을 "default"라는 이름을 가진 서블릿을 매핑해서 정적 자원을 처리하도록 구현하고 있다.
- 이 설정을 DispatcherServle에 재정의하여 JSP에 대한 요청은 처리하지 않으면서 그 외 모든 요청을 담당하도록 구현하는 것
- loadOnStartup 설정은 서블릿의 인스턴스를 생성하는 시점과 초기화를 담당하는 init() 메서드를 어느 시점에 호출할것인가 설정
  - 설정하지 않으면, 서블릿 인스턴스 생성과 초기화는 서블릿 컨테이너가 시작을 완료한 후 클라이언트의 요청이 최초로 발생하는 시점에 진행
    - 서블릿 컨테이너를 시작한 후 10분만에 클라이언트 요청이 최조 발생하면 그때 서블릿 인스턴스가 생성되고 초기화가 진행됨
  - 설정하는 경우는 서블릿 컨테이너가 시작하는 시점에 서블릿 인스턴스 생성과 초기화가 진행됨 (낮은 값일 수록 먼저 진행)