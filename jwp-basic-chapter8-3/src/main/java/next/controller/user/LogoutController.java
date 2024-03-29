package next.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.mvc.Controller;
import core.mvc.Model;
import core.mvc.ModelAndView;
import core.mvc.view.JspView;
import next.controller.UserSessionUtils;

public class LogoutController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
        return new ModelAndView(new Model(), new JspView("redirect:/"));
    }
}
