package com.project.Quiz.Contoller;

import com.project.Quiz.DTO.UserSummary;
import com.project.Quiz.Entity.User;
import com.project.Quiz.Security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @GetMapping("/me")
    public UserSummary getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        return new UserSummary(user.getId(), user.getName(), user.getEmail(), user.getRole().name());
    }
}