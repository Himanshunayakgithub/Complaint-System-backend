package com.complaint.controller;

import com.complaint.entity.User;
import com.complaint.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        user.setRole("USER");
        return ResponseEntity.ok(userRepo.save(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        return userRepo.findByEmail(email)
                .filter(user -> user.getPassword().equals(password))
                .map(ResponseEntity::ok)
                .orElseGet((Supplier<? extends ResponseEntity<User>>) ResponseEntity.status(401).body("Invalid credentials"));
    }
}
