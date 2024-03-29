package next.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import core.mvc.Model;
import core.mvc.ModelAndView;
import core.mvc.view.JspView;
import next.controller.UserSessionUtils;
import next.dao.UserDao;
import next.model.User;

public class UpdateFormUserController implements Controller {

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");
        UserDao userDao = new UserDao();
        User user = userDao.findByUserId(userId);
        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
        Model model = new Model();
        model.addAttribute("user", user);
        return new ModelAndView(model, new JspView("/user/updateForm.jsp"));
    }
}
