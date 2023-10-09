package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.db.DataBase;
import core.mvc.Controller;
import next.model.User;

public class LoginController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        User user = DataBase.findUserById(userId);
        if (user == null) {
            request.setAttribute("loginFailed", true);
            return "/user/login.jsp";
        }

        if (!user.matchPassword(password)) {
            request.setAttribute("loginFailed", true);
            return "/user/login.jsp";
        }

        HttpSession session = request.getSession();
        session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
        return "redirect:/";
    }
}