package org.example.expensetracker.controller;

import lombok.RequiredArgsConstructor;
import org.example.expensetracker.dto.ExpenseRequest;
import org.example.expensetracker.entity.Expense;
import org.example.expensetracker.service.ExpenseService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping()
    public Expense createExpense(@RequestBody ExpenseRequest expense) {
        return expenseService.createExpense(expense);
    }

    @PutMapping("/{expenseId}/category/{categoryId}")
    public Expense setCategoryToExpense(@PathVariable Long expenseId, @PathVariable Long categoryId) {
        return expenseService.setCategoryToExpense(expenseId, categoryId);
    }

    @GetMapping("/{id}")
    public Expense getExpenseById(@PathVariable Long id) {
        return expenseService.getExpenseById(id);
    }

    @GetMapping()
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable Long id, @RequestBody ExpenseRequest expense) {
        return expenseService.updateExpense(id, expense);
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }
}
