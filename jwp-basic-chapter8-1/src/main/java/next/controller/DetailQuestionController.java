package next.controller;

import core.mvc.Controller;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.dao.UserDao;
import next.model.Answer;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

        AnswerDao answerDao = new AnswerDao();
        List<Answer> findAllAnswer = answerDao.findAllByQuestionId(findQuestion.getQuestionId());
        req.setAttribute("answers", findAllAnswer);

        return "/qna/show.jsp";
    }
}
