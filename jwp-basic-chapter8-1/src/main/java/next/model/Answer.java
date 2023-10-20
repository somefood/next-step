package next.model;

import java.sql.Date;
import java.time.LocalDateTime;

public class Answer {

    private Long answerId;

    private String writer;

    private String contents;

    private Date createdDate;

    private Long questionId;

    public Answer(Long answerId, String writer, String contents, Date createdDate, Long questionId) {
        this.answerId = answerId;
        this.writer = writer;
        this.contents = contents;
        this.createdDate = createdDate;
        this.questionId = questionId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Long getQuestionId() {
        return questionId;
    }
}
