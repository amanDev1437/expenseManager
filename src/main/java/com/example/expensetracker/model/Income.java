package com.example.expensetracker.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "user")
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int incomeId;

    private String source;

    private int amount;

    @ManyToOne
    private User user;
}
