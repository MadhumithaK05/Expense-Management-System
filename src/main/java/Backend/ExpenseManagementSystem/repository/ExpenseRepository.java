package Backend.ExpenseManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Backend.ExpenseManagementSystem.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

}
