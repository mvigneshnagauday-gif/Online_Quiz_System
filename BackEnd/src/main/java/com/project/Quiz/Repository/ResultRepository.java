package com.project.Quiz.Repository;

import com.project.Quiz.Entity.Course;
import com.project.Quiz.Entity.Result;
import com.project.Quiz.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Long> {

    // All attempts for a user in a course (used to build the levels-page status: passed/failed/none)
    List<Result> findByUserAndCourse(User user, Course course);

    // All attempts for a user across every course (used by the "Results" page)
    List<Result> findByUserOrderByAttemptedAtDesc(User user);

    // Most recent attempt for a specific user+course+level (used by review.html and result.html)
    Optional<Result> findFirstByUserAndCourseAndLevelOrderByAttemptedAtDesc(User user, Course course, Integer level);
    @org.springframework.data.jpa.repository.Query(value =
        "SELECT u.id AS userId, u.name AS name, " +
        "       COUNT(DISTINCT CASE WHEN r.passed = 1 THEN CONCAT(r.course_id, '-', r.level) END) AS levelsPassed, " +
        "       ROUND(AVG(r.score), 0) AS averageScore " +
        "FROM users u " +
        "JOIN results r ON r.user_id = u.id " +
        "GROUP BY u.id, u.name " +
        "ORDER BY levelsPassed DESC, averageScore DESC " +
        "LIMIT 50",
        nativeQuery = true)
    List<Object[]> findLeaderboardRaw();
}