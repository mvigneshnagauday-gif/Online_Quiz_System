package com.project.Quiz.Repository;

import com.project.Quiz.Entity.Course;
import com.project.Quiz.Entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByCourseAndLevel(Course course, Integer level);
}