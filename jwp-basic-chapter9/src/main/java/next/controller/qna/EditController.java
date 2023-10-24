package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditController extends AbstractController {

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long questionId = Long.parseLong(request.getParameter("questionId"));

        QuestionDao questionDao = QuestionDao.getQuestionDao();
        Question findQuestion = questionDao.findById(questionId);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !user.isOwner(findQuestion)) {
            throw new RuntimeException("소유자가 아닙니다.");
        }

        Question updateQuestion = new Question(
                questionId,
                findQuestion.getWriter(),
                request.getParameter("title"),
                request.getParameter("contents"),
                findQuestion.getCreatedDate(),
                findQuestion.getCountOfComment()
        );

        questionDao.update(updateQuestion);

        return jsonView();
    }
}
