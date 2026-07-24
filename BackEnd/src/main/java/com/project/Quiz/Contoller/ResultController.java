package com.project.Quiz.Contoller;

import com.project.Quiz.DTO.LeaderboardEntry;
import com.project.Quiz.DTO.ResultSummary;
import com.project.Quiz.Entity.Result;
import com.project.Quiz.Repository.ResultRepository;
import com.project.Quiz.Security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import java.util.List;
import java.util.stream.Collectors;
 
@RestController
@RequestMapping("/api")
public class ResultController {
 
    @Autowired
    private ResultRepository resultRepository;
 
    // Matches results.js -> GET /api/results
    // Returns every attempt for the logged-in user, most recent first.
    @GetMapping("/results")
    public List<ResultSummary> getMyResults(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<Result> results = resultRepository.findByUserOrderByAttemptedAtDesc(userDetails.getUser());
 
        return results.stream()
                .map(r -> new ResultSummary(
                        r.getCourse().getName(),
                        r.getLevel(),
                        r.getScore(),
                        r.getPassed(),
                        r.getAttemptedAt()
                ))
                .collect(Collectors.toList());
    }
 
    // Matches leaderboard.js -> GET /api/leaderboard
    // Ranks all users by distinct levels passed, then average score.
    @GetMapping("/leaderboard")
    public List<LeaderboardEntry> getLeaderboard() {
        List<Object[]> rows = resultRepository.findLeaderboardRaw();
 
        return rows.stream()
                .map(row -> new LeaderboardEntry(
                        ((Number) row[0]).longValue(),           // userId
                        (String) row[1],                          // name
                        ((Number) row[2]).longValue(),            // levelsPassed
                        row[3] != null ? ((Number) row[3]).doubleValue() : 0.0  // averageScore
                ))
                .collect(Collectors.toList());
    }
}
 