package next.model;

import java.sql.Date;
import java.time.LocalDateTime;

public class Question {

    private Long questionId;

    private String writer;

    private String title;

    private String contents;

    private Date createdDate;

    private Integer countOfAnswer;

    public Question(Long questionId, String writer, String title, String contents, Date createdDate, Integer countOfAnswer) {
        this.questionId = questionId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
        this.countOfAnswer = countOfAnswer;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Integer getCountOfAnswer() {
        return countOfAnswer;
    }
}