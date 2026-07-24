package com.project.Quiz.Contoller;


import com.project.Quiz.DTO.ProgressResponse;
import com.project.Quiz.Entity.Course;
import com.project.Quiz.Repository.CourseRepository;
import com.project.Quiz.Security.CustomUserDetails;
import com.project.Quiz.Service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ProgressService progressService;

    // Matches levels.js -> GET /api/progress?course=Java
    @GetMapping
    public ResponseEntity<?> getProgress(@RequestParam String course,
                                          @AuthenticationPrincipal CustomUserDetails userDetails) {
        Course courseEntity = courseRepository.findByName(course).orElse(null);

        if (courseEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Course not found: " + course));
        }

        ProgressResponse response = progressService.getProgress(userDetails.getUser(), courseEntity);
        return ResponseEntity.ok(response);
    }
}