package next.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.mvc.Controller;
import core.mvc.Model;
import core.mvc.ModelAndView;
import core.mvc.view.JspView;
import next.controller.UserSessionUtils;
import next.dao.UserDao;
import next.model.User;

public class LoginController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        UserDao userDao = new UserDao();
        User user = userDao.findByUserId(userId);

        Model model = new Model();

        if (user == null) {
            model.addAttribute("loginFailed", true);
            return new ModelAndView(model, new JspView("/user/login.jsp"));
        }
        if (user.matchPassword(password)) {
            HttpSession session = req.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            return new ModelAndView(model, new JspView("redirect:/"));
        } else {
            model.addAttribute("loginFailed", true);
            return new ModelAndView(model, new JspView("/user/login.jsp"));
        }
    }
}
