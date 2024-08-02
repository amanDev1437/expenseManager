package com.example.expensetracker.controller;

import com.example.expensetracker.model.Expense;
import com.example.expensetracker.model.Income;
import com.example.expensetracker.model.User;
import com.example.expensetracker.service.ExpenseSevice;
import com.example.expensetracker.service.IncomeService;
import com.example.expensetracker.service.Message;
import com.example.expensetracker.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ExpenseSevice expenseSevice;

    @Autowired
    private IncomeService incomeService;

    @ModelAttribute
    public void addCommonData(Model model, Principal principal){

        User user = userService.getUser(principal.getName());

        model.addAttribute("user",user);



    }


    @RequestMapping("/home")
    public String dashboard(Model model, Principal principal){


        model.addAttribute("totalExpense",expenseSevice.getTotalExpense(principal));

        model.addAttribute("totalIncome",incomeService.getTotalIncome(principal));

        model.addAttribute("topExpense",expenseSevice.getTopExpense(principal));


        return "userDashboard";

    }

    @GetMapping("/addExpense")
    public String gotoAddExpense(Model model){

        model.addAttribute("title","Add Expense");

        return "addExpense";

    }

    @PostMapping("/addExpense")
    public String addExpense(@ModelAttribute Expense expense,Principal principal,HttpSession session){

        try{
            expenseSevice.addExpense(expense,principal);
            session.setAttribute("message",new Message("Expense is added!!","alert-success"));
        }catch (Exception e){
            System.out.println("ERROR"+e.getMessage());
            e.printStackTrace();
            session.setAttribute("message",new Message("something went wrong !!","alert-danger"));
        }

        return "redirect:/user/addExpense";

    }

    @GetMapping("/addIncome")
    public String gotoAddIncome(Model model){

        model.addAttribute("title","Add Income");

        return "addIncome";

    }

    @PostMapping("/addIncome")
    public String addIncome(@ModelAttribute Income income, Principal principal, HttpSession session){

        try{
            incomeService.addIncome(income,principal);
            session.setAttribute("message",new Message("Income is added!!","alert-success"));
        }catch (Exception e){
            System.out.println("ERROR"+e.getMessage());
            e.printStackTrace();
            session.setAttribute("message",new Message("something went wrong !!","alert-danger"));
        }

        return "redirect:/user/addIncome";
    }

    @GetMapping("/viewExpense/{page}")
    public String gotoViewExpense(@PathVariable("page") Integer page, Model model, Principal principal){
        model.addAttribute("title","View Expense");

        User user = userService.getUser(principal.getName());
        Page<Expense> expenseList = expenseSevice.getAllExpense(user,page);

        model.addAttribute("expenseList",expenseList);
        model.addAttribute("currentPage",page);

        model.addAttribute("totalPage",expenseList.getTotalPages());

        return "viewExpense";

    }

    @GetMapping("/viewIncome/{page}")
    public String gotoViewIncome(@PathVariable Integer page, Model model,Principal principal){

        model.addAttribute("title","View Income");

        User user = userService.getUser(principal.getName());
        Page<Income> incomeList = incomeService.getAllIncome(user,page);

        model.addAttribute("incomeList",incomeList);
        model.addAttribute("currentPage",page);

        model.addAttribute("totalPage",incomeList.getTotalPages());

        return "viewIncome";

    }


}


