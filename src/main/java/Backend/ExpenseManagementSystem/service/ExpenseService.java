package Backend.ExpenseManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import Backend.ExpenseManagementSystem.entity.Expense;
import Backend.ExpenseManagementSystem.repository.ExpenseRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public Expense addExpense(Expense expense) {
        log.info("Adding new expense: {}", expense.getTitle());
        return expenseRepository.save(expense);
    }

    public Iterable<Expense> getAllExpenses() {
        log.info("Fetching all expenses");
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id).orElse(null);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public Expense updateExpense(Long id, Expense updatedExpense) {
        return expenseRepository.findById(id).map(expense -> {
            expense.setTitle(updatedExpense.getTitle());
            expense.setAmount(updatedExpense.getAmount());
            return expenseRepository.save(expense);
        }).orElse(null);
    }

    public Page<Expense> getExpensesinPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return expenseRepository.findAll(pageable);
    }
}
