package Backend.ExpenseManagementSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Backend.ExpenseManagementSystem.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
