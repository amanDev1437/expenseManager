package com.example.expensetracker.service;

import com.example.expensetracker.model.Expense;
import com.example.expensetracker.model.User;
import com.example.expensetracker.repository.ExpenseRepository;
import com.example.expensetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class ExpenseSevice {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseRepository expenseRepository;


    public void addExpense(Expense expense, Principal principal){

        String email = principal.getName();

        User user = userRepository.findByEmail(email);

        expense.setUser(user);

        user.getExpenseList().add(expense);

        userRepository.save(user);

    }

    public int getTotalExpense(Principal principal){

        int totalExpense = 0;

        String email = principal.getName();

        User user = userRepository.findByEmail(email);

        List<Expense> expenses = user.getExpenseList();

        for(Expense exp : expenses){

            totalExpense = totalExpense+exp.getAmount();

        }

        return totalExpense;


    }


    public Page<Expense> getAllExpense(User user, Integer page) {

        Pageable pageable = PageRequest.of(page,3);

        return expenseRepository.findExpenseByUserId(user.getUserId(),pageable);

    }

    public List<Expense> getTopExpense(Principal principal){

        String email = principal.getName();

        User user = userRepository.findByEmail(email);

        List<Expense> expenses = user.getExpenseList();

        expenses.sort((e1,e2)->Integer.compare(e2.getAmount(),e1.getAmount()));

        return expenses.stream().limit(5).toList();


    }
}
