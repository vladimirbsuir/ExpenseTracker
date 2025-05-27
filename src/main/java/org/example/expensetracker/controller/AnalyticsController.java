package org.example.expensetracker.controller;

import lombok.RequiredArgsConstructor;
import org.example.expensetracker.dto.DailyExpenseDto;
import org.example.expensetracker.dto.ExpenseReportDto;
import org.example.expensetracker.dto.TopExpenseDto;
import org.example.expensetracker.service.AnalyticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/analytics")
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    @GetMapping("/balance")
    public Float getTotalBalance() {
        return analyticsService.getTotalBalance();
    }

    @GetMapping("/expenses/by-category")
    public List<ExpenseReportDto> getExpensesByCategory(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return analyticsService.getExpensesByCategory(start, end);
    }

    @GetMapping("/expenses/daily")
    public List<DailyExpenseDto> getDailyExpenses(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return analyticsService.getDailyExpenses(start, end);
    }

    @GetMapping("/expenses/top")
    public List<TopExpenseDto> getTopExpenses(
            @RequestParam(defaultValue = "5") Integer limit) {
        return analyticsService.getTopExpenses(limit);
    }
}
