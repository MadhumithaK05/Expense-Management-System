package Backend.ExpenseManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Backend.ExpenseManagementSystem.entity.Expense;
import Backend.ExpenseManagementSystem.repository.ExpenseRepository;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public Iterable<Expense> getAllExpenses() {
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
}
