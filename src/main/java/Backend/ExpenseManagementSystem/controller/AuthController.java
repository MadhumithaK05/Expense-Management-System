package Backend.ExpenseManagementSystem.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Backend.ExpenseManagementSystem.entity.User;
import Backend.ExpenseManagementSystem.repository.UserRepository;
import Backend.ExpenseManagementSystem.security.JwtUtil;
import Backend.ExpenseManagementSystem.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder PasswordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String, String> body) {
        String name = body.get("name");
        String email = body.get("email");
        String password = PasswordEncoder.encode(body.get("password"));

        if (userRepository.findByEmail(email).isPresent()) {
            return new ResponseEntity<>("Email already exists", HttpStatus.CONFLICT);
            // return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
            // both can be used
        }

        userService.addUser(User.builder().name(name).email(email).password(password).build());
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        var userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()){
            return new ResponseEntity<>("User not Registered", HttpStatus.UNAUTHORIZED);
        }

        User user = userOpt.get();
        if (!PasswordEncoder.matches(password, user.getPassword())) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
        String token = jwtUtil.generateToken(email);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
