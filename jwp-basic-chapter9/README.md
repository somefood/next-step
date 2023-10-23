#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.

1. 서블릿 컨테이너가 올라가면서 @WebListner 어노테이션을 찾으며 ServletContextListner의 contextInitialized 메서드를 호출한다.
  - 이때 클래스패스에 있는 jwp.sql의 스크립트를 실행해서 DB 테이블 생성 및 데이터를 넣어준다.
2. 서블릿 컨테이너는 서블릿들을 등록하기 위해 @WebServlet 어노테이션이 있는 클래스들을 탐색한다. 이때 name이 dispatcher인 서블릿을 찾았고, urlPatterns = "/"를 선언하여 defaultServlet으로 등록한다.
3. 이후 서블릿의 init() 메서드를 실행하여 초기화 작업을 하는데, url과 컨트롤러가 매핑되어 있는 RequestMapping을 초기화 한다.

#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.

1. dispatcher라 선언한 서블릿에 들어와 service 메서드가 실행된다.
2. HttpServletRequest 객체에서 requestUri를 갖고와서 RequestMapping의 findController를 통해 "/"에 매핑되는 HomeController를 찾는다.
3. 찾아진 HomeController는 execute를 실행하는데, jspView에서 "home.jsp" 뷰가 지정되어 있고, addObject를 통해 "questions"에 질문들이 담긴다. 이후 ModelAndView 객체를 반환한다.
4. 서블릿은 View 객체를 꺼내서 render() 메서드를 모델과 함께 실행한다.
5. JspView의 경우엔 jsp 파일로 이동해주면서, request에 모델에 있던 값들을 담아준다.
6. 이후 jsp에서 모델의 값을 꺼내서 적절히 렌더링 해준다.

#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* 
