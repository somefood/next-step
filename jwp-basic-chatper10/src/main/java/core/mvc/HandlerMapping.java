package core.mvc;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {

    boolean isSupport(HttpServletRequest request);

    Object getHandler(HttpServletRequest request);
}
