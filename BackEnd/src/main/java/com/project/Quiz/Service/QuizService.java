package com.project.Quiz.Service;

import com.project.Quiz.DTO.*;
import com.project.Quiz.Entity.Course;
import com.project.Quiz.Entity.Question;
import com.project.Quiz.Entity.Result;
import com.project.Quiz.Entity.User;
import com.project.Quiz.Repository.CourseRepository;
import com.project.Quiz.Repository.QuestionRepository;
import com.project.Quiz.Repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private static final int PASS_THRESHOLD = 50; // percent

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private ProgressService progressService;

    private Course getCourseOrThrow(String courseName) {
        return courseRepository.findByName(courseName)
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + courseName));
    }

    // Used by quiz.html — never includes the correct answer, so it can't be
    // read out of the browser's network tab and used to cheat.
    public List<QuestionDTO> getQuestions(String courseName, Integer level) {
        Course course = getCourseOrThrow(courseName);

        return questionRepository.findByCourseAndLevel(course, level).stream()
                .map(q -> new QuestionDTO(
                        q.getId(),
                        q.getQuestionText(),
                        List.of(q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4())
                ))
                .collect(Collectors.toList());
    }

    public SubmitResponse submitQuiz(User user, SubmitRequest request) {
        Course course = getCourseOrThrow(request.getCourse());
        List<Question> questions = questionRepository.findByCourseAndLevel(course, request.getLevel());

        Map<Long, Question> questionMap = questions.stream()
                .collect(Collectors.toMap(Question::getId, q -> q));

        int correctCount = 0;
        StringBuilder answersJson = new StringBuilder();

        for (AnswerSubmission answer : request.getAnswers()) {
            Question question = questionMap.get(answer.getQuestionId());
            if (question == null) continue;

            Integer selected = answer.getSelectedOption();
            if (selected != null && selected.equals(question.getCorrectOption())) {
                correctCount++;
            }

            if (answersJson.length() > 0) answersJson.append(",");
            answersJson.append(answer.getQuestionId()).append(":").append(selected);
        }

        int total = questions.size();
        int score = total == 0 ? 0 : (int) Math.round((correctCount * 100.0) / total);
        boolean passed = score >= PASS_THRESHOLD;

        Result result = new Result();
        result.setUser(user);
        result.setCourse(course);
        result.setLevel(request.getLevel());
        result.setScore(score);
        result.setCorrectCount(correctCount);
        result.setTotalQuestions(total);
        result.setPassed(passed);
        result.setAutoSubmitted(Boolean.TRUE.equals(request.getAutoSubmitted()));
        result.setAnswersJson(answersJson.toString());

        resultRepository.save(result);

        // Unlock the next level if they passed (ProgressService caps at level 3)
        progressService.unlockNextLevelIfPassed(user, course, request.getLevel(), passed);

        boolean nextLevelUnlocked = passed && request.getLevel() < 3;

        return new SubmitResponse(score, correctCount, total, passed, nextLevelUnlocked);
    }

    // Used by review.html — shows the user's most recent attempt for this
    // level, with their selected answer next to the correct one.
    public List<ReviewQuestionDTO> getReview(User user, String courseName, Integer level) {
        Course course = getCourseOrThrow(courseName);

        Result result = resultRepository
                .findFirstByUserAndCourseAndLevelOrderByAttemptedAtDesc(user, course, level)
                .orElseThrow(() -> new IllegalArgumentException("No attempt found for this level yet"));

        Map<Long, Integer> selectedAnswers = parseAnswersJson(result.getAnswersJson());

        List<Question> questions = questionRepository.findByCourseAndLevel(course, level);

        return questions.stream().map(q -> {
            ReviewQuestionDTO dto = new ReviewQuestionDTO();
            dto.setQuestionText(q.getQuestionText());
            dto.setOptions(List.of(q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4()));
            dto.setCorrectOption(q.getCorrectOption());
            dto.setSelectedOption(selectedAnswers.get(q.getId()));
            return dto;
        }).collect(Collectors.toList());
    }

    // Used by solutions.html — the plain answer key, no attempt required.
    public List<ReviewQuestionDTO> getSolutions(String courseName, Integer level) {
        Course course = getCourseOrThrow(courseName);
        List<Question> questions = questionRepository.findByCourseAndLevel(course, level);

        return questions.stream().map(q -> {
            ReviewQuestionDTO dto = new ReviewQuestionDTO();
            dto.setQuestionText(q.getQuestionText());
            dto.setOptions(List.of(q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4()));
            dto.setCorrectOption(q.getCorrectOption());
            dto.setSelectedOption(null);
            return dto;
        }).collect(Collectors.toList());
    }

    // Parses "12:1,13:null,14:2" back into a lookup map
    private Map<Long, Integer> parseAnswersJson(String answersJson) {
        Map<Long, Integer> map = new HashMap<>();
        if (answersJson == null || answersJson.isBlank()) return map;

        for (String pair : answersJson.split(",")) {
            String[] parts = pair.split(":");
            if (parts.length != 2) continue;

            Long questionId = Long.parseLong(parts[0].trim());
            String value = parts[1].trim();
            map.put(questionId, "null".equals(value) ? null : Integer.parseInt(value));
        }
        return map;
    }
}