package org.example.expensetracker.controller;

import lombok.RequiredArgsConstructor;
import org.example.expensetracker.dto.ReminderRequest;
import org.example.expensetracker.service.ReminderService;
import org.springframework.web.bind.annotation.*;
import org.example.expensetracker.dto.ReminderResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reminders")
public class ReminderController {
    private final ReminderService reminderService;

    @PostMapping
    public ReminderResponse createReminder(@RequestBody ReminderRequest request) {
        return reminderService.createReminder(request);
    }

    @GetMapping
    public List<ReminderResponse> getAllReminders() {
        return reminderService.getAllReminders();
    }

    @GetMapping("/{id}")
    public ReminderResponse getReminderById(@PathVariable Long id) {
        return reminderService.getReminderById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteReminder(@PathVariable Long id) {
        reminderService.deleteReminder(id);
    }

    @PutMapping("/deactivate/{id}")
    public void deactivateReminder(@PathVariable Long id) {
        reminderService.deactivateReminder(id);
    }
}
