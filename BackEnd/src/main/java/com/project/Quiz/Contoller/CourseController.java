package com.project.Quiz.Contoller;



import com.project.Quiz.DTO.CourseSummary;
import com.project.Quiz.Entity.Course;
import com.project.Quiz.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    // Optional endpoint — dashboard.html currently hardcodes the 5 course
    // cards, but this lets you switch to loading them dynamically later.
    @GetMapping
    public List<CourseSummary> getAllCourses() {
        List<Course> courses = courseRepository.findAll();

        return courses.stream()
                .map(c -> new CourseSummary(c.getId(), c.getName(), c.getDescription()))
                .collect(Collectors.toList());
    }
}