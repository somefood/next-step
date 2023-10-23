package next.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import core.mvc.Model;
import core.mvc.ModelAndView;
import core.mvc.view.JspView;
import next.controller.UserSessionUtils;
import next.dao.UserDao;

public class ListUserController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        JspView jspView = null;
        Model model = new Model();

        if (!UserSessionUtils.isLogined(req.getSession())) {
            return new ModelAndView(model, new JspView("redirect:/users/loginForm"));
        }

        UserDao userDao = new UserDao();
        model.addAttribute("users", userDao.findAll());
        return new ModelAndView(model, new JspView("/user/list.jsp"));
    }
}
