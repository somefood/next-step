package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import core.mvc.Model;
import core.mvc.ModelAndView;
import core.mvc.View;
import core.mvc.view.JspView;
import next.dao.AnswerDao;
import next.dao.QuestionDao;

public class ShowController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long questionId = Long.parseLong(req.getParameter("questionId"));
        QuestionDao questionDao = new QuestionDao();
        AnswerDao answerDao = new AnswerDao();

        Model model = new Model();
        model.addAttribute("question", questionDao.findById(questionId));
        model.addAttribute("answers", answerDao.findAllByQuestionId(questionId));

        View view = new JspView("/qna/show.jsp");

        return new ModelAndView(model, view);
    }
}
