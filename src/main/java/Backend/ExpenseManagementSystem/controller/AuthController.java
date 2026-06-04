package Backend.ExpenseManagementSystem.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Backend.ExpenseManagementSystem.dto.LoginRequest;
import Backend.ExpenseManagementSystem.dto.RegisterRequest;
import Backend.ExpenseManagementSystem.entity.User;
import Backend.ExpenseManagementSystem.repository.UserRepository;
import Backend.ExpenseManagementSystem.security.JwtUtil;
import Backend.ExpenseManagementSystem.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest body) {
        String email = body.getEmail();
        body.setPassword(passwordEncoder.encode(body.getPassword()));
        if (userRepository.findByEmail(email).isPresent()) {
            return new ResponseEntity<>("Email already exists", HttpStatus.CONFLICT);
            // return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
            // both can be used
        }

        userService.addUser(body);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest body) {
        String email = body.getEmail();
        String password = body.getPassword();

        var userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>("User not Registered", HttpStatus.UNAUTHORIZED);
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
        String token = jwtUtil.generateToken(email);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
