package com.example.expensetracker.controller;

import com.example.expensetracker.model.User;
import com.example.expensetracker.service.Message;
import com.example.expensetracker.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;


    @GetMapping
    public String redirectToHome(){

        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(){

        return "home";
    }



    @GetMapping("/signup")
    public String gotoSignup(Model model){

        model.addAttribute("user",new User());

        return "signup";
    }

    @PostMapping("/signup")
    public String createUser(@ModelAttribute User user, HttpSession session){

        if(userService.saveUser(user)!=null){

            session.setAttribute("message",new Message("Successfully Registered!!","alert-success"));

        }

        return "signup";
    }

    @GetMapping("/login")
    public String gotoLogin(){

        return "login";

    }

}
