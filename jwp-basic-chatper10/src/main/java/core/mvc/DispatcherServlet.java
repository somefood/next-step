package core.mvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.nmvc.AnnotationHandlerMapping;
import core.nmvc.HandlerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private List<HandlerMapping> handlerMappings = new ArrayList<>();
    private List<HandlerAdapter> handlerAdapters = new ArrayList<>();
    private LegacyHandlerMapping lhm;
    private AnnotationHandlerMapping ahm;

    @Override
    public void init() throws ServletException {
        lhm = new LegacyHandlerMapping();
        lhm.initMapping();
        ahm = new AnnotationHandlerMapping("next.controller");
        ahm.initialize();

        handlerMappings.add(lhm);
        handlerMappings.add(ahm);

        handlerAdapters.add(new AnnotationAdapter());
        handlerAdapters.add(new ControllerAdapter());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Object handler = null;
            ModelAndView mav = null;
            for (HandlerMapping handlerMapping : handlerMappings) {
                if (handlerMapping.isSupport(req)) {
                    handler = handlerMapping.getHandler(req);
                    break;
                }
            }

            for (HandlerAdapter handlerAdapter : handlerAdapters) {
                if (handlerAdapter.supports(handler)) {
                    mav = handlerAdapter.getHandler(handler, req, resp);
                    break;
                }
            }

            if (mav != null) {
                render(req, resp, mav);
            } else {
                throw new ServletException("유효하지 않은 요청입니다.");
            }
        } catch (Throwable e) {
            throw new ServletException(e.getMessage());
        }
    }

    private void render(HttpServletRequest req, HttpServletResponse resp, ModelAndView mav) throws Exception {
        View view = mav.getView();
        view.render(mav.getModel(), req, resp);
    }
}
