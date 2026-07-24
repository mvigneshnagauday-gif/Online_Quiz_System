package com.project.Quiz.DTO;

public class LevelResult {

    private Integer level;
    private Integer score;
    private Boolean passed;

    public LevelResult() {}

    public LevelResult(Integer level, Integer score, Boolean passed) {
        this.level = level;
        this.score = score;
        this.passed = passed;
    }

    public Integer getLevel() { return level; }
    public void setLevel(Integer level) { this.level = level; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public Boolean getPassed() { return passed; }
    public void setPassed(Boolean passed) { this.passed = passed; }
}