package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ShowEditController extends AbstractController {

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Long questionId = Long.parseLong(request.getParameter("questionId"));

        QuestionDao questionDao = new QuestionDao();
        Question findQuestion = questionDao.findById(questionId);

        return jspView("/qna/editForm.jsp").addObject("question", findQuestion);
    }
}
