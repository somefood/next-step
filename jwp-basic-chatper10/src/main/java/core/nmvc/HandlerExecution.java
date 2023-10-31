package core.nmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.ModelAndView;

import java.lang.reflect.Method;

public class HandlerExecution {

    private Method method;
    private Class<?> clazz;

    public HandlerExecution(Method method, Class<?> clazz) {
        this.method = method;
        this.clazz = clazz;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return (ModelAndView) method.invoke(clazz.getConstructor().newInstance(), request, response);
    }
}
