package com.project.Quiz.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_progress", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "course_id"})
})
public class UserProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

   
    @Column(nullable = false)
    private Integer highestLevelUnlocked = 1;

    public UserProgress() {}

    public UserProgress(User user, Course course) {
        this.user = user;
        this.course = course;
        this.highestLevelUnlocked = 1;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public Integer getHighestLevelUnlocked() { return highestLevelUnlocked; }
    public void setHighestLevelUnlocked(Integer highestLevelUnlocked) { this.highestLevelUnlocked = highestLevelUnlocked; }
}