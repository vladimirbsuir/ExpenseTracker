package org.example.expensetracker.controller;

import lombok.RequiredArgsConstructor;
import org.example.expensetracker.dto.BudgetRequest;
import org.example.expensetracker.entity.Budget;
import org.example.expensetracker.service.BudgetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/budgets")
public class BudgetController {
    private final BudgetService budgetService;

    @PostMapping
    public Budget createBudget(@RequestBody BudgetRequest budget) {
        return budgetService.createBudget(budget);
    }

    @PutMapping("/{budgetId}/category/{categoryId}")
    public Budget assignBudgetToCategory(@PathVariable Long budgetId, @PathVariable Long categoryId) {
        return budgetService.assignBudgetToCategory(budgetId, categoryId);
    }

    @GetMapping
    public List<Budget> getAllBudgets() {
        return budgetService.findAllBudgets();
    }

    @GetMapping("/{id}")
    public Budget getBudgetById(@PathVariable Long id) {
        return budgetService.findBudgetById(id);
    }

    @PutMapping("/{id}")
    public Budget updateBudget(@PathVariable Long id, @RequestBody BudgetRequest budget) {
        return budgetService.updateBudget(id, budget);
    }

    @DeleteMapping("/{id}")
    public void deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
    }
}
