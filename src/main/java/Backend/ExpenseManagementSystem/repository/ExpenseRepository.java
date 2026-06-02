package Backend.ExpenseManagementSystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Backend.ExpenseManagementSystem.entity.Expense;
import Backend.ExpenseManagementSystem.entity.User;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUser(User user);

    Optional<Expense> findByIdAndUser(Long id, User user);

    Page<Expense> findByUser(User user, Pageable pageable);
}
