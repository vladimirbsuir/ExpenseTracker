package org.example.expensetracker.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String message;

    @Column
    private LocalDate date;

    @Column
    @Enumerated(EnumType.STRING)
    private ReminderType type;

    @Column
    private boolean active;
}
