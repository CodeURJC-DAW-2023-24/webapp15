package es.codeurjc.webapp15.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.webapp15.model.User;
import es.codeurjc.webapp15.repository.UserRepository;
import es.codeurjc.webapp15.service.UserSession;
import jakarta.annotation.PostConstruct;

@Controller
public class UserController {

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private UserSession session;

    @PostConstruct
    public void init() {
        usersRepository.save(new User("edwardKennedy", "edu123456", "hola"));
        usersRepository.save(new User("hughjackman", "maninthemiddle", "hola"));
    }

    @PostMapping("/user/new")
    public String createUser(Model model,User user) {
        try {
            usersRepository.save(user);
            session.setUser(user);

            return "redirect:/"; // Redirect to homepage or user profile page after successful login
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/login")
    public String login() {
        User user = session.getUser();
        if (user != null) {
            return "redirect:/";
        } else {
            return "login";
        }
    }

    @PostMapping("/user")
    public String login(Model model, @RequestParam String email, @RequestParam String password) {
        List<User> users = usersRepository.findByEmail(email);
        if (!users.isEmpty()) {
            User user = users.get(0); // Assuming email is unique and always returns at most one user
            if (user.getPassword().equals(password)) {
                session.setUser(user);
                return "redirect:/";
            }
        }
        model.addAttribute("loginError", "Invalid email or password");
        return "login"; // Return to login page if authentication fails
    }
}
