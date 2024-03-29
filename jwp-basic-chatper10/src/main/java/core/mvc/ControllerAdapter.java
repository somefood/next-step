package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerAdapter implements HandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof Controller;
    }

    @Override
    public ModelAndView handle(Object handler, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Controller controller = (Controller) handler;

        return controller.execute(request, response);
    }
}
