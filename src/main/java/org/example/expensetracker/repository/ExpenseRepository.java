package org.example.expensetracker.repository;

import org.example.expensetracker.dto.DailyExpenseDto;
import org.example.expensetracker.dto.ExpenseReportDto;
import org.example.expensetracker.dto.TopExpenseDto;
import org.example.expensetracker.entity.Category;
import org.example.expensetracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByCategory(Category category);

    @Query("SELECT new org.example.expensetracker.dto.ExpenseReportDto(e.category.name, SUM(e.amount)) " +
            "FROM Expense e WHERE e.date BETWEEN :startDate AND :endDate GROUP BY e.category.name")
    List<ExpenseReportDto> findTotalExpensesByCategoryBetweenDates(@Param("startDate") LocalDate startDate,
                                                                   @Param("endDate") LocalDate endDate);

    @Query("SELECT new org.example.expensetracker.dto.DailyExpenseDto(e.date, SUM(e.amount)) " +
            "FROM Expense e WHERE e.date BETWEEN :startDate AND :endDate GROUP BY e.date ORDER BY e.date ASC")
    List<DailyExpenseDto> findDailyExpensesBetweenDates(@Param("startDate") LocalDate startDate,
                                                        @Param("endDate") LocalDate endDate);

    /*@Query("SELECT new org.example.expensetracker.dto.TopExpenseDto(e.name, e.description, e.amount, e.category.name, e.date) " +
            "FROM Expense e ORDER BY e.amount DESC LIMIT :limit")
    List<TopExpenseDto> findTopExpenses(@Param("limit") Integer limit);*/
}
