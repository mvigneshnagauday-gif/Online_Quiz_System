package com.project.Quiz.DTO;

import java.util.List;

public class ProgressResponse {

    private Integer highestLevelUnlocked;
    private List<LevelResult> results;

    public ProgressResponse() {}

    public ProgressResponse(Integer highestLevelUnlocked, List<LevelResult> results) {
        this.highestLevelUnlocked = highestLevelUnlocked;
        this.results = results;
    }

    public Integer getHighestLevelUnlocked() { return highestLevelUnlocked; }
    public void setHighestLevelUnlocked(Integer highestLevelUnlocked) { this.highestLevelUnlocked = highestLevelUnlocked; }

    public List<LevelResult> getResults() { return results; }
    public void setResults(List<LevelResult> results) { this.results = results; }
}