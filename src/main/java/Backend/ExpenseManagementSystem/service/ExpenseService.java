package Backend.ExpenseManagementSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import Backend.ExpenseManagementSystem.entity.Expense;
import Backend.ExpenseManagementSystem.entity.User;
import Backend.ExpenseManagementSystem.repository.ExpenseRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private UserService userService;

    public Expense addExpense(Expense expense) {
        User currentUser = userService.getCurrentUser();
        expense.setUser(currentUser);
        log.info("Adding new expense: {}", expense.getTitle());
        return expenseRepository.save(expense);
    }

    public List<Expense> getMyExpenses() {
        User currentUser = userService.getCurrentUser();
        return expenseRepository.findByUser(currentUser);
    }

    public Expense getExpenseById(Long id) {
        User currentUser = userService.getCurrentUser();
        return expenseRepository
                .findByIdAndUser(id, currentUser)
                .orElse(null);
    }

    public void deleteExpense(Long id) {
        User currentUser = userService.getCurrentUser();
        Expense expense = expenseRepository
                .findByIdAndUser(id, currentUser)
                .orElseThrow(()
                        -> new RuntimeException("Expense not found"));
        expenseRepository.delete(expense);
    }

    public Expense updateExpense(Long id, Expense updatedExpense) {
        User currentUser = userService.getCurrentUser();
        return expenseRepository
                .findByIdAndUser(id, currentUser)
                .map(expense -> {
                    expense.setTitle(updatedExpense.getTitle());
                    expense.setAmount(updatedExpense.getAmount());
                    return expenseRepository.save(expense);
                })
                .orElse(null);
    }

    public Page<Expense> getExpensesinPage(int page, int size) {
        User currentUser = userService.getCurrentUser();
        Pageable pageable = PageRequest.of(page, size);
        return expenseRepository.findByUser(currentUser, pageable);
    }
}
