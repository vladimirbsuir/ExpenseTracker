package org.example.expensetracker.service;

import lombok.RequiredArgsConstructor;
import org.example.expensetracker.dto.DailyExpenseDto;
import org.example.expensetracker.dto.ExpenseReportDto;
import org.example.expensetracker.dto.TopExpenseDto;
import org.example.expensetracker.entity.Expense;
import org.example.expensetracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final ExpenseRepository expenseRepository;

    public List<ExpenseReportDto> getExpensesByCategory(LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findTotalExpensesByCategoryBetweenDates(startDate, endDate);
    }

    public List<DailyExpenseDto> getDailyExpenses(LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findDailyExpensesBetweenDates(startDate, endDate);
    }

    public List<TopExpenseDto> getTopExpenses(Integer limit) {
        return null;//expenseRepository.findTopExpenses(limit);
    }

    public Float getTotalBalance() {
        Float totalExpenses = expenseRepository.findAll().stream()
                .map(Expense::getAmount)
                .reduce(0f, Float::sum);
        return -totalExpenses;
    }
}
