package Backend.ExpenseManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import Backend.ExpenseManagementSystem.dto.RegisterRequest;
import Backend.ExpenseManagementSystem.entity.User;
import Backend.ExpenseManagementSystem.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User addUser(RegisterRequest user) {
        log.info("Adding new user: {}", user.getName());
        User userEntity = new User();
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        return userRepository.save(userEntity);
    }

    public Iterable<User> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // public User updateUser(Long id, User updatedUser) {
    //     return userRepository.findById(id).map(user -> {
    //         user.setName(updatedUser.getName());
    //         user.setEmail(updatedUser.getEmail());
    //         return userRepository.save(user);
    //     }).orElse(null);
    // }
    public User getCurrentUser() {
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
