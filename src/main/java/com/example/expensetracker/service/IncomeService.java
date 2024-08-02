package com.example.expensetracker.service;

import com.example.expensetracker.model.Expense;
import com.example.expensetracker.model.Income;
import com.example.expensetracker.model.User;
import com.example.expensetracker.repository.IncomeRepository;
import com.example.expensetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class IncomeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    public void addIncome(Income income, Principal principal){

        String email = principal.getName();

        User user = userRepository.findByEmail(email);

        income.setUser(user);

        user.getIncomeList().add(income);

        userRepository.save(user);
    }

    public int getTotalIncome(Principal principal){

        int totalIncome = 0;

        String email = principal.getName();

        User user = userRepository.findByEmail(email);

        List<Income> incomes = user.getIncomeList();

        for(Income inc : incomes){

            totalIncome = totalIncome+inc.getAmount();

        }

        return totalIncome;

    }

    public Page<Income> getAllIncome(User user, Integer page) {

        Pageable pageable = PageRequest.of(page,3);

        return incomeRepository.findIncomeByUserId(user.getUserId(),pageable);

    }
}
