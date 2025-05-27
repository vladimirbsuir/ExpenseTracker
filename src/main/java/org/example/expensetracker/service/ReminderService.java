package org.example.expensetracker.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.expensetracker.dto.ReminderRequest;
import org.example.expensetracker.dto.ReminderResponse;
import org.example.expensetracker.entity.Reminder;
import org.example.expensetracker.repository.ReminderRepository;
import org.example.expensetracker.util.ReminderMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReminderService {
    private final ReminderRepository reminderRepository;
    private final ReminderMapper reminderMapper;

    public org.example.expensetracker.dto.ReminderResponse createReminder(ReminderRequest reminderRequest) {
        Reminder reminder = reminderMapper.toEntity(reminderRequest);
        reminder = reminderRepository.save(reminder);
        return reminderMapper.toResponse(reminder);
    }

    public List<ReminderResponse> getAllReminders() {
        return reminderRepository.findAll().stream().map(reminderMapper::toResponse).toList();
    }

    public ReminderResponse getReminderById(Long id) {
        return reminderMapper.toResponse(reminderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reminder not found")));
    }

    public void deleteReminder(Long id) {
        reminderRepository.deleteById(id);
    }

    public void deactivateReminder(Long id) {
        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reminder not found"));
        reminder.setActive(false);
        reminderRepository.save(reminder);
    }
}
