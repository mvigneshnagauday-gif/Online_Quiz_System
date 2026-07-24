package com.project.Quiz.DTO;

import java.util.List;

public class QuestionDTO {

    private Long id;
    private String questionText;
    private List<String> options;

    public QuestionDTO() {}

    public QuestionDTO(Long id, String questionText, List<String> options) {
        this.id = id;
        this.questionText = questionText;
        this.options = options;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }
}