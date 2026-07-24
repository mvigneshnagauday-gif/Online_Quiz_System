package com.project.Quiz.Repository;

import com.project.Quiz.Entity.Course;
import com.project.Quiz.Entity.User;
import com.project.Quiz.Entity.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Userprogressrepository extends JpaRepository<UserProgress, Long> {

    Optional<UserProgress> findByUserAndCourse(User user, Course course);
}