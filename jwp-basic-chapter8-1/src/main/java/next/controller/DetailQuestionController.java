package next.controller;

import core.mvc.Controller;
import next.dao.QuestionDao;
import next.dao.UserDao;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DetailQuestionController implements Controller {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String questionId = req.getParameter("questionId");
        if (questionId == null) {
            return "redirect:/";
        }

        QuestionDao questionDao = new QuestionDao();
        Question findQuestion = questionDao.findByQuestionId(Long.parseLong(questionId));
        req.setAttribute("question", findQuestion);

        return "/qna/show.jsp";
    }
}
