package Backend.ExpenseManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Backend.ExpenseManagementSystem.entity.Expense;
import Backend.ExpenseManagementSystem.service.ExpenseService;

@RestController
@RequestMapping("/v1/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/add")
    ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {
        return new ResponseEntity<>(expenseService.addExpense(expense), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    ResponseEntity<Iterable<Expense>> getAllExpenses() {
        return new ResponseEntity<>(expenseService.getAllExpenses(), HttpStatus.OK); 
    }
    @GetMapping("/get/{id}")
    ResponseEntity<Expense> getExpense(@PathVariable Long id) {
        return new ResponseEntity<>(expenseService.getExpenseById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    ResponseEntity<Expense> updateExpense(@RequestBody Expense expense, @PathVariable Long id) {
        return new ResponseEntity<>(expenseService.updateExpense(id, expense), HttpStatus.OK);
    }

}
