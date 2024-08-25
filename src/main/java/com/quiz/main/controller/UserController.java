package com.quiz.main.controller;

import com.quiz.main.model.UserDto; // Assuming you have a UserDto class
import com.quiz.main.model.User;
import com.quiz.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("userDto") UserDto userDto) {
        userService.registerNewUser(userDto); // Adjusted to call registerNewUser
        return "redirect:/login";
    }


    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // Removed duplicate /showRegister endpoint

}
