package org.example.expensetracker.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.expensetracker.entity.Category;
import org.example.expensetracker.entity.Expense;
import org.example.expensetracker.repository.CategoryRepository;
import org.example.expensetracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    public Category findCategoryByName(String name) {
        return categoryRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Category updateCategory(Long id, Category category) {
        Category categoryToUpdate = findCategoryById(id);

        if (category.getName() != null) categoryToUpdate.setName(category.getName());

        return categoryRepository.save(categoryToUpdate);
    }

    public void deleteCategory(Long id) {
        Category category = findCategoryById(id);

        for (Expense expense : expenseRepository.findByCategory(category)) {
            expense.setCategory(null);
        }

        categoryRepository.delete(category);
    }
}
