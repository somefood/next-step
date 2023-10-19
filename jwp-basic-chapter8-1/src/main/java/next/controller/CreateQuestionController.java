package next.controller;

import core.mvc.Controller;
import next.dao.QuestionDao;
import next.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class CreateQuestionController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(CreateQuestionController.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        if (!req.getMethod().equalsIgnoreCase("post")) {
            logger.error("이 컨트롤러는 post만 가능");
            return null;
        }

        Question newQuestion = new Question(
                null,
                req.getParameter("writer"),
                req.getParameter("title"),
                req.getParameter("contents"),
                new Date(System.currentTimeMillis()),
                0
        );

        QuestionDao questionDao = new QuestionDao();
        questionDao.insert(newQuestion);

        return "redirect:/";
    }
}
