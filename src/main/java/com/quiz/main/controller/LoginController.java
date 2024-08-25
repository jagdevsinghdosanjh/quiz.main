package com.quiz.main.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.quiz.main.model.User;
import com.quiz.main.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ModelAndView login(String username, String password, HttpSession session) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                // Assuming password is stored as plain text for demonstration; typically, you'd compare a hashed password.
                session.setAttribute("currentUser", user);
                if (null == user.getRole()) {
                    // Handle unexpected role
                    ModelAndView modelAndView = new ModelAndView("login");
                    modelAndView.addObject("error", "User role is not recognized.");
                    return modelAndView;
                } else // Adjust role check to match stored role strings
                switch (user.getRole()) {
                    case "ROLE_ADMIN" -> {
                        return new ModelAndView("redirect:/admin/home"); // Redirect to admin home page
                    }
                    case "ROLE_USER" -> {
                        return new ModelAndView("redirect:/user/home"); // Redirect to user home page
                    }
                    default -> {
                        // Handle unexpected role
                        ModelAndView modelAndView = new ModelAndView("login");
                        modelAndView.addObject("error", "User role is not recognized.");
                        return modelAndView;
                    }
                }
            }
        }
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("error", "Invalid username or password");
        return modelAndView; // Stay on the login page and show an error message
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login"; // Redirect to login page after logout
    }
}
