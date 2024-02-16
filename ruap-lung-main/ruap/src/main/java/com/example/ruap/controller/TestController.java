package com.example.ruap.controller;


import com.example.ruap.model.User;
import com.example.ruap.machine_learning.CallRequestResponseService;
import com.example.ruap.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    private final UserRepository userRepository;

    public TestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }


    @GetMapping("/user")
    public ResponseEntity<String> userAccess(Authentication authentication) {
        try {
            String username = authentication.getName();

            String predictionInputs = CallRequestResponseService.getPredictionInputs();

            String template = "User %s, please input the following values:\n%s";

            return ResponseEntity.ok(String.format(template, username, predictionInputs));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error occurred while fetching prediction inputs.");
        }
    }


    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public Optional<User> getUserById(@PathVariable String id, Authentication authentication) {
        return userRepository.findById(id);
    }
}

