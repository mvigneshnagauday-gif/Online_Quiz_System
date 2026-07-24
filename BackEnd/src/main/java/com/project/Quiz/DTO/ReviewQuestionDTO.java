package com.project.Quiz.DTO;

import java.util.List;

public class ReviewQuestionDTO {

    private String questionText;
    private List<String> options;
    private Integer correctOption;
    private Integer selectedOption; // null on solutions.html (no attempt context needed there)
    private String explanation;     // optional, used by solutions.html

    public ReviewQuestionDTO() {}

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }

    public Integer getCorrectOption() { return correctOption; }
    public void setCorrectOption(Integer correctOption) { this.correctOption = correctOption; }

    public Integer getSelectedOption() { return selectedOption; }
    public void setSelectedOption(Integer selectedOption) { this.selectedOption = selectedOption; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }
}