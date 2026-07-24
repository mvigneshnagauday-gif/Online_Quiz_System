package com.project.Quiz.DTO;

public class LeaderboardEntry {

    private Long userId;
    private String name;
    private Long levelsPassed;
    private Double averageScore;

    public LeaderboardEntry() {}

    public LeaderboardEntry(Long userId, String name, Long levelsPassed, Double averageScore) {
        this.userId = userId;
        this.name = name;
        this.levelsPassed = levelsPassed;
        this.averageScore = averageScore;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getLevelsPassed() { return levelsPassed; }
    public void setLevelsPassed(Long levelsPassed) { this.levelsPassed = levelsPassed; }

    public Double getAverageScore() { return averageScore; }
    public void setAverageScore(Double averageScore) { this.averageScore = averageScore; }
}