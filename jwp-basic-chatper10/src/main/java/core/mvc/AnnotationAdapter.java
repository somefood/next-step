package core.mvc;

import core.annotation.Controller;
import core.nmvc.HandlerExecution;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AnnotationAdapter implements HandlerAdapter {

    @Override
    public boolean supports(Object object) {
        return object instanceof HandlerExecution;
    }

    @Override
    public ModelAndView getHandler(Object handler, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HandlerExecution execution = (HandlerExecution) handler;
        return execution.handle(request, response);
    }
}
