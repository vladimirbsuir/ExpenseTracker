package org.example.expensetracker.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.example.expensetracker.dto.ExpenseCsvDto;
import org.example.expensetracker.dto.ExpenseRequest;
import org.example.expensetracker.entity.Budget;
import org.example.expensetracker.entity.Category;
import org.example.expensetracker.entity.Expense;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CsvService {
    private final ExpenseService expenseService;
    private final CategoryService categoryService;
    private final BudgetService budgetService;

    public byte[] exportExpensesToCsv() throws IOException {
        List<Expense> expenses = expenseService.getAllExpenses();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(outputStream))) {
            writer.writeNext(new String[]{"Name","Amount", "Date", "Description", "Category", "Budget Amount"});

            for (Expense expense : expenses) {
                String budgetAmount = expense.getCategory() != null && expense.getCategory().getBudget() != null ? expense.getCategory().getBudget().getAmount().toString()
                        : "";

                writer.writeNext(new String[]{
                        expense.getName(),
                        expense.getAmount().toString(),
                        expense.getDate().toString(),
                        expense.getDescription(),
                        expense.getCategory() != null ? expense.getCategory().getName() : "",
                        budgetAmount});
            }
        }

        return outputStream.toByteArray();
    }

    public void importExpensesFromCsv(MultipartFile file) throws IOException, CsvValidationException {
        List<ExpenseCsvDto> dtos = getExpenseCsvDtos(file);

        for (ExpenseCsvDto dto : dtos) {
            Category categoryRequest = new Category();
            categoryRequest.setName(dto.getCategoryName());

            Category category = categoryService.existsByName(dto.getCategoryName())
                    ? categoryService.findCategoryByName(dto.getCategoryName())
                    : categoryService.createCategory(categoryRequest);

            if (dto.getBudgetAmount() != null && !dto.getBudgetAmount().isEmpty()) {
                //Budget budget = budgetService.findBudgetByCategory(category));
                Budget budget = new Budget();
                budget.setAmount(Float.parseFloat(dto.getBudgetAmount()));
                budget.setPeriod(LocalDate.now().withDayOfMonth(1));
                budget.setCategory(category);

                category.setBudget(budget);
                categoryService.updateCategory(category.getId(), category);
            }

            ExpenseRequest expense = new ExpenseRequest();
            expense.setName(dto.getName());
            expense.setAmount(Float.parseFloat(dto.getAmount()));
            expense.setDate(LocalDate.parse(dto.getDate()));
            expense.setDescription(dto.getDescription());
            expense.setCategory(category);

            expenseService.createExpense(expense);
        }
    }

    private static List<ExpenseCsvDto> getExpenseCsvDtos(MultipartFile file) throws IOException, CsvValidationException {
        List<ExpenseCsvDto> dtos = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            reader.readNext();
            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {
                ExpenseCsvDto dto = new ExpenseCsvDto();
                dto.setName(nextLine[0]);
                dto.setAmount(nextLine[1]);
                dto.setDate(nextLine[2]);
                dto.setDescription(nextLine[3]);
                dto.setCategoryName(nextLine[4]);
                dto.setBudgetAmount(nextLine.length > 5 ? nextLine[5] : null);
                dtos.add(dto);
            }
        }
        return dtos;
    }
}
