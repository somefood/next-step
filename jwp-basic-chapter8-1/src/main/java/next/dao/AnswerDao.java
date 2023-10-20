package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import next.model.Answer;

import java.util.List;

public class AnswerDao {

    public Answer findByAnswerId(Long answerId) {
        String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM answers WHERE answerId = ?";

        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        RowMapper<Answer> rowMapper = rs -> new Answer(
                rs.getLong("answerId"),
                rs.getString("writer"),
                rs.getString("contents"),
                rs.getDate("createdDate"),
                rs.getLong("questionId")
        );

        return jdbcTemplate.queryForObject(sql, rowMapper, answerId);
    }

    public List<Answer> findAllByQuestionId(Long questionId) {
        String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM answers WHERE questionId = ?";

        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        RowMapper<Answer> rowMapper = rs -> new Answer(
                rs.getLong("answerId"),
                rs.getString("writer"),
                rs.getString("contents"),
                rs.getDate("createdDate"),
                rs.getLong("questionId")
        );

        return jdbcTemplate.query(sql, rowMapper, questionId);
    }
}
