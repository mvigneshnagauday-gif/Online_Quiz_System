package com.project.Quiz.Contoller;



import com.project.Quiz.DTO.*;
import com.project.Quiz.Entity.Course;
import com.project.Quiz.Entity.UserProgress;
import com.project.Quiz.Repository.CourseRepository;
import com.project.Quiz.Security.CustomUserDetails;
import com.project.Quiz.Service.ProgressService;
import com.project.Quiz.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private ProgressService progressService;

    @Autowired
    private CourseRepository courseRepository;

    // Blocks a user from fetching/submitting a level they haven't unlocked
    // yet, even if they call the API directly (not just hiding the button
    // in the UI — this was flagged earlier as important).
    private boolean isLevelUnlocked(CustomUserDetails userDetails, String courseName, Integer level) {
        Course course = courseRepository.findByName(courseName).orElse(null);
        if (course == null) return false;

        UserProgress progress = progressService.getOrCreateProgress(userDetails.getUser(), course);
        return level <= progress.getHighestLevelUnlocked();
    }

    // Matches quiz.js -> GET /api/quiz/questions?course=Java&level=1
    @GetMapping("/questions")
    public ResponseEntity<?> getQuestions(@RequestParam String course,
                                           @RequestParam Integer level,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (!isLevelUnlocked(userDetails, course, level)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "This level is locked. Pass the previous level first."));
        }

        List<QuestionDTO> questions = quizService.getQuestions(course, level);
        return ResponseEntity.ok(questions);
    }

    // Matches quiz.js -> POST /api/quiz/submit
    @PostMapping("/submit")
    public ResponseEntity<?> submitQuiz(@RequestBody SubmitRequest request,
                                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (!isLevelUnlocked(userDetails, request.getCourse(), request.getLevel())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "This level is locked. Pass the previous level first."));
        }

        try {
            SubmitResponse response = quizService.submitQuiz(userDetails.getUser(), request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    // Matches review.js -> GET /api/quiz/review?course=Java&level=1
    @GetMapping("/review")
    public ResponseEntity<?> getReview(@RequestParam String course,
                                        @RequestParam Integer level,
                                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            List<ReviewQuestionDTO> review = quizService.getReview(userDetails.getUser(), course, level);
            return ResponseEntity.ok(review);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }

    // Matches solutions.js -> GET /api/quiz/solutions?course=Java&level=1
    @GetMapping("/solutions")
    public ResponseEntity<?> getSolutions(@RequestParam String course,
                                           @RequestParam Integer level,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<ReviewQuestionDTO> solutions = quizService.getSolutions(course, level);
        return ResponseEntity.ok(solutions);
    }
}