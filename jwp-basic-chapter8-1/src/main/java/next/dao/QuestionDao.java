package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import next.model.Question;

import java.util.List;

public class QuestionDao {

    public void insert(Question question) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        String sql = "INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfAnswer) VALUES (?, ?, ?, ?, ?);";

        jdbcTemplate.update(sql, question.getWriter(), question.getTitle(), question.getContents(), question.getCreatedDate(), 0);
    }

    public void update() {

    }

    public Question findByQuestionId(Long questionID) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS WHERE questionId = ?";

        RowMapper<Question> rowMapper = rs -> new Question(
                rs.getLong("questionId"),
                rs.getString("writer"),
                rs.getString("title"),
                rs.getString("contents"),
                rs.getDate("createdDate"),
                rs.getInt("countOfAnswer")
        );

        return jdbcTemplate.queryForObject(sql, rowMapper, questionID);
    }

    public List<Question> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS";

        RowMapper<Question> rowMapper = rs -> new Question(
                rs.getLong("questionId"),
                rs.getString("writer"),
                rs.getString("title"),
                rs.getString("contents"),
                rs.getDate("createdDate"),
                rs.getInt("countOfAnswer")
        );

        return jdbcTemplate.query(sql, rowMapper);
    }
}
