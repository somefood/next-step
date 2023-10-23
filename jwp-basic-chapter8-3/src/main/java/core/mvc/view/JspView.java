package core.mvc.view;

import core.mvc.Model;
import core.mvc.View;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class JspView implements View {

    private String viewName;
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    public JspView(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void render(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        Map<String, Object> maps = model.getMaps();

        for (Map.Entry<String, Object> m : maps.entrySet()) {
            request.setAttribute(m.getKey(), m.getValue());
        }

        move(viewName, request, response);
    }

    private void move(String viewName, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            resp.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return;
        }

        RequestDispatcher rd = req.getRequestDispatcher(viewName);
        rd.forward(req, resp);
    }
}
