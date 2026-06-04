package Backend.ExpenseManagementSystem.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import Backend.ExpenseManagementSystem.dto.ExpenseRequest;
import Backend.ExpenseManagementSystem.dto.ExpenseResponse;
import Backend.ExpenseManagementSystem.entity.Expense;
import Backend.ExpenseManagementSystem.entity.User;
import Backend.ExpenseManagementSystem.repository.ExpenseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserService userService;

    public ExpenseResponse addExpense(ExpenseRequest expense) {
        User currentUser = userService.getCurrentUser();
        Expense expenseEntity = Expense.builder()
                .title(expense.getTitle())
                .amount(expense.getAmount())
                .user(currentUser)
                .build();
        log.info("Adding new expense: {}", expenseEntity.getTitle());
        Expense savedExpense = expenseRepository.save(expenseEntity);
        return ExpenseResponse.builder()
                .id(savedExpense.getId())
                .title(savedExpense.getTitle())
                .amount(savedExpense.getAmount())
                .build();
    }

    public List<ExpenseResponse> getMyExpenses() {
        User currentUser = userService.getCurrentUser();
        return expenseRepository.findByUser(currentUser).stream()
                .map(expense -> ExpenseResponse.builder()
                .id(expense.getId())
                .title(expense.getTitle())
                .amount(expense.getAmount())
                .build())
                .collect(Collectors.toList());
    }

    public ExpenseResponse getExpenseById(Long id) {
        User currentUser = userService.getCurrentUser();
        return expenseRepository
                .findByIdAndUser(id, currentUser)
                .map(expense -> ExpenseResponse.builder()
                .id(expense.getId())
                .title(expense.getTitle())
                .amount(expense.getAmount())
                .build())
                .orElseThrow(()
                        -> new RuntimeException("Expense not found"));
    }

    public void deleteExpense(Long id) {
        User currentUser = userService.getCurrentUser();
        Expense expense = expenseRepository
                .findByIdAndUser(id, currentUser)
                .orElseThrow(()
                        -> new RuntimeException("Expense not found"));
        expenseRepository.delete(expense);
    }

    @Transactional
    public ExpenseResponse updateExpense(Long id, ExpenseRequest updatedExpense) {
        User currentUser = userService.getCurrentUser();
        return expenseRepository
                .findByIdAndUser(id, currentUser)
                .map(expense -> {
                    expense.setTitle(updatedExpense.getTitle());
                    expense.setAmount(updatedExpense.getAmount());
                    return ExpenseResponse.builder()
                        .id(expense.getId())
                        .title(expense.getTitle())
                        .amount(expense.getAmount())
                        .build();
                })
                .orElseThrow(()
                        -> new RuntimeException("Expense not found"));
    }

    public Page<ExpenseResponse> getExpensesInPage(int page, int size) {
        User currentUser = userService.getCurrentUser();
        Pageable pageable = PageRequest.of(page, size);
        return expenseRepository.findByUser(currentUser, pageable).map(expense -> ExpenseResponse.builder()
                .id(expense.getId())
                .title(expense.getTitle())
                .amount(expense.getAmount())
                .build());
    }
}
