package com.ExpenseTrackerApp.ExpenseTrackerApp.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expense> expenses;

    public User(String name, String email){
        this.name = name;
        this.email = email;
        this.expenses = new ArrayList<>();
    }

    public void addExpense(Expense expense){
        this.expenses.add(expense);
    }

    public List<Expense> getExpensesByDates(LocalDate date1, LocalDate date2){
        if (date1.isAfter(date2)){
            LocalDate temp = date2;
            date2 = date1;
            date1 = temp;
        }
        LocalDate finalDate1 = date1;
        LocalDate finalDate2 = date2;
        if (! this.expenses.isEmpty()) {
            return this.expenses.stream().filter(e -> e.getDate().isAfter(finalDate1) &&
                    e.getDate().isBefore(finalDate2)).collect(Collectors.toList());
        }
        return null;
    }

    public List<Expense> getExpensesByCategory(Category category){
        if (! this.expenses.isEmpty()){
            return this.expenses.stream().filter(e -> e.getCategory().equals(category))
                    .collect(Collectors.toList());
        }
        return null;
    }

}
