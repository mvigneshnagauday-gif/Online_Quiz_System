package com.project.Quiz.DTO;

public class SubmitResponse {

    private Integer score;
    private Integer correctCount;
    private Integer totalQuestions;
    private Boolean passed;
    private Boolean nextLevelUnlocked;

    public SubmitResponse() {}

    public SubmitResponse(Integer score, Integer correctCount, Integer totalQuestions,
                           Boolean passed, Boolean nextLevelUnlocked) {
        this.score = score;
        this.correctCount = correctCount;
        this.totalQuestions = totalQuestions;
        this.passed = passed;
        this.nextLevelUnlocked = nextLevelUnlocked;
    }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public Integer getCorrectCount() { return correctCount; }
    public void setCorrectCount(Integer correctCount) { this.correctCount = correctCount; }

    public Integer getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(Integer totalQuestions) { this.totalQuestions = totalQuestions; }

    public Boolean getPassed() { return passed; }
    public void setPassed(Boolean passed) { this.passed = passed; }

    public Boolean getNextLevelUnlocked() { return nextLevelUnlocked; }
    public void setNextLevelUnlocked(Boolean nextLevelUnlocked) { this.nextLevelUnlocked = nextLevelUnlocked; }
}