package com.fitassist.backend.controller;

// DÜZELTME: Adresler 'backend' eklentisiyle güncellendi!
import com.fitassist.backend.dto.LoginRequest;
import com.fitassist.backend.model.User;
import com.fitassist.backend.service.UserService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fitassist.backend.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User savedUser = userService.registerUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @Autowired
    private UserRepository userRepository;
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUserProfile(@PathVariable Long id, @RequestBody User updatedData) {
        
        // Kullanıcıyı veritabanında arıyoruz
        java.util.Optional<User> optionalUser = userRepository.findById(id);

        // Eğer kullanıcı veritabanında varsa if bloğu çalışır
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            
            user.setHeight(updatedData.getHeight());
            user.setWeight(updatedData.getWeight());
            user.setAge(updatedData.getAge());
            user.setGoal(updatedData.getGoal());
            user.setImageUrl(updatedData.getImageUrl());
            
            userRepository.save(user);
            return ResponseEntity.ok(user);
            
        // Eğer kullanıcı yoksa else bloğu çalışır
        } else {
            return ResponseEntity.badRequest().body("Kullanici bulunamadi");
        }
    }
}