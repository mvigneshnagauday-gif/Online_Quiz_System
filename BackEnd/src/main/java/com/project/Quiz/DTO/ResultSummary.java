package com.project.Quiz.DTO;

import java.time.LocalDateTime;

public class ResultSummary {

    private String course;
    private Integer level;
    private Integer score;
    private Boolean passed;
    private LocalDateTime attemptedAt;

    public ResultSummary() {}

    public ResultSummary(String course, Integer level, Integer score, Boolean passed, LocalDateTime attemptedAt) {
        this.course = course;
        this.level = level;
        this.score = score;
        this.passed = passed;
        this.attemptedAt = attemptedAt;
    }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public Integer getLevel() { return level; }
    public void setLevel(Integer level) { this.level = level; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public Boolean getPassed() { return passed; }
    public void setPassed(Boolean passed) { this.passed = passed; }

    public LocalDateTime getAttemptedAt() { return attemptedAt; }
    public void setAttemptedAt(LocalDateTime attemptedAt) { this.attemptedAt = attemptedAt; }
}