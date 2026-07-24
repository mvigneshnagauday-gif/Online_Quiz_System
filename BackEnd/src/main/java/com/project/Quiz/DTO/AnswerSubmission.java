package com.project.Quiz.DTO;

public class AnswerSubmission {

    private Long questionId;
    private Integer selectedOption; // null if the user left it unanswered

    public AnswerSubmission() {}

    public Long getQuestionId() { return questionId; }
    public void setQuestionId(Long questionId) { this.questionId = questionId; }

    public Integer getSelectedOption() { return selectedOption; }
    public void setSelectedOption(Integer selectedOption) { this.selectedOption = selectedOption; }
}