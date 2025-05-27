package org.example.expensetracker.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.expensetracker.dto.ExpenseRequest;
import org.example.expensetracker.entity.Category;
import org.example.expensetracker.entity.Expense;
import org.example.expensetracker.repository.ExpenseRepository;
import org.example.expensetracker.util.ExpenseMapper;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final CategoryService categoryService;
    private final ExpenseMapper expenseMapper;

    public Expense createExpense(ExpenseRequest expense) {
        if (expense.getDate() == null) expense.setDate(LocalDate.now());
        if (expense.getCategory() != null) {
            try {
                Category category = categoryService.findCategoryByName(expense.getCategory().getName());
                expense.setCategory(category);
            } catch (EntityNotFoundException ignored) {}
        }

        Expense expenseToSave = expenseMapper.toEntity(expense);

        return expenseRepository.save(expenseToSave);
    }

    public Expense setCategoryToExpense(Long expenseId, Long categoryId) {
        Expense expense = getExpenseById(expenseId);
        Category category = categoryService.findCategoryById(categoryId);

        expense.setCategory(category);
        category.getExpenses().add(expense);

        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Expense not found"));
    }

    public Expense updateExpense(Long id, ExpenseRequest expense) {
        Expense expenseToUpdate = getExpenseById(id);

        if (expense.getName() != null) expenseToUpdate.setName(expense.getName());
        if (expense.getDescription() != null) expenseToUpdate.setDescription(expense.getDescription());
        if (expense.getAmount() != null) expenseToUpdate.setAmount(expense.getAmount());
        if (expense.getCategory() != null) expenseToUpdate.setCategory(expense.getCategory());

        return expenseRepository.save(expenseToUpdate);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }
}
