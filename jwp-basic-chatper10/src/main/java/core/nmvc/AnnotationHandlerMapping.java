package core.nmvc;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Maps;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import org.reflections.Reflections;

public class AnnotationHandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        for (Object o : basePackage) {
            String currentPackage = (String) o;
            Reflections reflections = new Reflections(currentPackage);

            Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);

            for (Class<?> controller : controllers) {
                Method[] methods = controller.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                        HandlerKey handlerKey = new HandlerKey(requestMapping.value(), requestMapping.method());

                        handlerExecutions.put(handlerKey, new HandlerExecution(method, controller));
                    }
                }
            }
        }
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
        return handlerExecutions.get(new HandlerKey(requestUri, rm));
    }
}
