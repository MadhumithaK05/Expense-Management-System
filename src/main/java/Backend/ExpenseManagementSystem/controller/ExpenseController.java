package Backend.ExpenseManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Backend.ExpenseManagementSystem.dto.ExpenseRequest;
import Backend.ExpenseManagementSystem.dto.ExpenseResponse;
import Backend.ExpenseManagementSystem.service.ExpenseService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/expenses")
@SuppressWarnings("unused")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    //insert expense
    @PostMapping("")
    ResponseEntity<ExpenseResponse> addExpense(@Valid @RequestBody ExpenseRequest body) {
        return new ResponseEntity<>(expenseService.addExpense(body), HttpStatus.CREATED);
    }

    //get all expenses
    @GetMapping("")
    ResponseEntity<Iterable<ExpenseResponse>> getAllExpenses() {
        return new ResponseEntity<>(expenseService.getMyExpenses(), HttpStatus.OK);
    }

    //get expense by id
    @GetMapping("/{id}")
    ResponseEntity<ExpenseResponse> getExpense(@PathVariable Long id) {
        return new ResponseEntity<>(expenseService.getExpenseById(id), HttpStatus.OK);
    }

    //delete expense by id
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //update expense by id
    @PutMapping("/{id}")
    ResponseEntity<ExpenseResponse> updateExpense(@RequestBody ExpenseRequest body, @PathVariable Long id) {
        return new ResponseEntity<>(expenseService.updateExpense(id, body), HttpStatus.OK);
    }

    @GetMapping("/page")
    ResponseEntity<Page<ExpenseResponse>> getExpensesInPage(@RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(expenseService.getExpensesInPage(page, size), HttpStatus.OK);
    }
}
