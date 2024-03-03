package es.codeurjc.webapp15.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.webapp15.model.Concert;
import es.codeurjc.webapp15.model.Ticket;
import es.codeurjc.webapp15.model.User;
import es.codeurjc.webapp15.repository.TicketRepository;
import es.codeurjc.webapp15.repository.UserRepository;
import es.codeurjc.webapp15.service.UserService;
import es.codeurjc.webapp15.service.UserSession;
import jakarta.annotation.PostConstruct;

@Controller
public class UserController {

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private UserService userService; // Assuming you have a UserService

    @Autowired
    private UserSession session;

    @Autowired
    private TicketRepository ticketRepository;

    @PostConstruct
    public void init() {
        User admin = new User("admin", "admin", "ADMIN");
        admin.setEmail("admin@admin.com");
        usersRepository.save(admin);

        User user = new User("user", "user", "USER");
        user.setEmail("user@user.com");
        usersRepository.save(user);
    }

    @GetMapping("/logout")
    public String cerrarSesion(Model model) {

        session.setUser(null);
        return "redirect:/";
    }

    @GetMapping("/signup")
    public String registro(Model model) {

        User user = session.getUser();
        if (user != null) {
            return "redirect:/";
        } else {
            return "registro";
        }
    }

    @PostMapping("/user/new")
    public String createUser(Model model, @RequestParam MultipartFile Image, @RequestParam String Name, @RequestParam String Email, @RequestParam String password) {
        try {
            // Check if user already exists with the given email
            List<User> existingUsers = usersRepository.findByEmail(Email);
            if (!existingUsers.isEmpty()) {
                // User exists, so we return an error message
                model.addAttribute("error", "El email ya está en uso");
                return "registro"; // Return back to the registration form
            }

            User user = new User(Name, password, "USER");
            user.setEmail(Email);

            if (!Image.isEmpty()) {
                user.setImg_user(BlobProxy.generateProxy(Image.getInputStream(), Image.getSize()));
                user.setImage(true);
            }
            
            usersRepository.save(user);
            session.setUser(user);
            
            return "redirect:/"; // Redirect to homepage or user profile page after successful login
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ha ocurrido un error.");
            return "register"; // Redirect back to the registration page with an error message
        }
    }


    @GetMapping("/login")
    public String login(Model model) {
        User user = session.getUser();
        if (user != null) {
            return "redirect:/";
        } else {
            return "login";
        }
    }

    @GetMapping("/user/image/{userId}")
    public ResponseEntity<byte[]> getUserImage(@PathVariable("userId") Long userId) {
        try {
            User user = usersRepository.findById(userId).get(); // Implement this method to find user by ID
            if (user != null && user.getImg_user() != null) {
                byte[] imageBytes = user.getImg_user().getBytes(1, (int) user.getImg_user().length());
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG); // Or the correct content type of your image
                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        User user = session.getUser(); // Assuming 'session' is your way of retrieving the currently logged-in user.
        if (user != null) {
            List<Ticket> ticketList = ticketRepository.findByUserId(user.getId(), PageRequest.of(0, 6)).getContent();
            // Fetch the first 6 tickets for the user
            // Adjust the end index if the list size is less than 6
            int endIndex = Math.min(ticketList.size(), 6);
            List<Ticket> userTickets = ticketList.subList(0, endIndex);

            user.setTickets(userTickets);
            session.setUser(user);

            // Directly add the tickets to the model
            model.addAttribute("tickets", userTickets);

            return "perfil"; // Ensure "perfil" is the correct view name
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/user/update/name")
    public ResponseEntity<?> updateUserName(@RequestBody Map<String, String> payload) {
        Long userId = session.getUser().getId();
        // Extract the new name from the payload
        String newName = payload.get("value");
        User updatedUser = userService.updateUserName(userId, newName);
        if (updatedUser != null) {
            session.setUser(updatedUser);
            return ResponseEntity.ok().body("User name updated successfully");
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    @PostMapping("/user/update/email")
    public ResponseEntity<?> updateUserEmail(@RequestBody Map<String, String> payload) {
        Long userId = session.getUser().getId();
        // Extract the new email from the payload
        String newEmail = payload.get("value");
        User updatedUser = userService.updateUserEmail(userId, newEmail);
        if (updatedUser != null) {
            session.setUser(updatedUser);
            return ResponseEntity.ok().body("User email updated successfully");
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    @PostMapping("/user/update/image")
    public ResponseEntity<?> updateUserImage(@RequestParam("imageFile") MultipartFile file) {
        try {
            User user = session.getUser();

            user.setImg_user(BlobProxy.generateProxy(file.getInputStream(), file.getSize()));
            user.setImage(true);
            usersRepository.save(user);

            session.setUser(user);

            return ResponseEntity.ok("Image updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update image");
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
        model.addAttribute("error", "Email o Contraseña no válido");
        return "login"; // Return to login page if authentication fails
    }
}
