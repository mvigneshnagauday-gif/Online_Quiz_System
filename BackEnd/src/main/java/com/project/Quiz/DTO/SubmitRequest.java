package com.project.Quiz.DTO;

import java.util.List;

public class SubmitRequest {

    private String course;
    private Integer level;
    private List<AnswerSubmission> answers;
    private Boolean autoSubmitted = false;

    public SubmitRequest() {}

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public Integer getLevel() { return level; }
    public void setLevel(Integer level) { this.level = level; }

    public List<AnswerSubmission> getAnswers() { return answers; }
    public void setAnswers(List<AnswerSubmission> answers) { this.answers = answers; }

    public Boolean getAutoSubmitted() { return autoSubmitted; }
    public void setAutoSubmitted(Boolean autoSubmitted) { this.autoSubmitted = autoSubmitted; }
}