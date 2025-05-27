package org.example.expensetracker.repository;

import org.example.expensetracker.entity.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    List<Reminder> findByActive(boolean b);
}
