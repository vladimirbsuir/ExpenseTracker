package org.example.expensetracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "budget")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Float amount;

    @Column(nullable = false)
    private LocalDate period;

    @OneToOne(mappedBy = "budget")
    @JsonIgnoreProperties("budget")
    private Category category;
}
