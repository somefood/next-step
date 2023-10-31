package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {

    ModelAndView getHandler(HandlerMapping handlerMapping, HttpServletRequest request, HttpServletResponse response);
}
