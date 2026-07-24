package com.project.Quiz.Service;

import com.project.Quiz.DTO.LevelResult;
import com.project.Quiz.DTO.ProgressResponse;
import com.project.Quiz.Entity.Course;
import com.project.Quiz.Entity.Result;
import com.project.Quiz.Entity.User;
import com.project.Quiz.Entity.UserProgress;
import com.project.Quiz.Repository.ResultRepository;
import com.project.Quiz.Repository.Userprogressrepository;
import com.project.Quiz.Repository.Userprogressrepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgressService {

    @Autowired
    private Userprogressrepository userProgressRepository;

    @Autowired
    private ResultRepository resultRepository;

    // Fetches the UserProgress row for this user+course, creating it
    // (starting at Level 1 unlocked) if it doesn't exist yet.
    public UserProgress getOrCreateProgress(User user, Course course) {
        return userProgressRepository.findByUserAndCourse(user, course)
                .orElseGet(() -> {
                    UserProgress progress = new UserProgress(user, course);
                    return userProgressRepository.save(progress);
                });
    }

    // Builds the response levels.js expects: highestLevelUnlocked + the
    // most recent pass/fail per level attempted so far.
    public ProgressResponse getProgress(User user, Course course) {
        UserProgress progress = getOrCreateProgress(user, course);

        List<Result> attempts = resultRepository.findByUserAndCourse(user, course);

        // Keep only the most recent attempt per level
        List<LevelResult> levelResults = attempts.stream()
                .collect(Collectors.toMap(
                        Result::getLevel,
                        r -> r,
                        (existing, incoming) -> incoming.getAttemptedAt().isAfter(existing.getAttemptedAt()) ? incoming : existing
                ))
                .values().stream()
                .sorted(Comparator.comparing(Result::getLevel))
                .map(r -> new LevelResult(r.getLevel(), r.getScore(), r.getPassed()))
                .collect(Collectors.toList());

        return new ProgressResponse(progress.getHighestLevelUnlocked(), levelResults);
    }

    // Called by QuizService right after a passing attempt. Unlocks the next
    // level, but never goes past level 3 (the max).
    public void unlockNextLevelIfPassed(User user, Course course, int completedLevel, boolean passed) {
        if (!passed) return;

        UserProgress progress = getOrCreateProgress(user, course);
        int nextLevel = completedLevel + 1;

        if (nextLevel > progress.getHighestLevelUnlocked() && nextLevel <= 3) {
            progress.setHighestLevelUnlocked(nextLevel);
            userProgressRepository.save(progress);
        }
    }
}