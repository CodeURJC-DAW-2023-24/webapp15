package es.codeurjc.webapp15.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import es.codeurjc.webapp15.model.Ticket;
import es.codeurjc.webapp15.model.User;
import es.codeurjc.webapp15.service.TicketService;
import es.codeurjc.webapp15.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @ModelAttribute("user")
    public void addAttributes(Model model, HttpServletRequest request){

        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            Optional <User> user = userService.findByEmail(principal.getName());
            if (user.isPresent()){
                if (user.get().isRole("USER")){
                    model.addAttribute("user", true);
                }
                if (user.get().isRole("ADMIN")){
                    model.addAttribute("admin", true);
                }
            }
            model.addAttribute("logged", true);
            model.addAttribute("userName", principal.getName());
        } else {
            model.addAttribute("logged", false);
        }
    }

    @ModelAttribute("tickets")
    public List<Ticket> globalTicketsModel(Model model, HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            Optional<User> user = userService.findByEmail(principal.getName());
            if (user.isPresent()) {
                return user.get().getTickets();
            }
        }
        return null;
    }

    @GetMapping("/signup")
    public String signup(Model model, HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();

        if (principal == null) {
            return "signup";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/user/new")
    public String createUser(Model model, @RequestParam MultipartFile image, @RequestParam String name,
            @RequestParam String email, @RequestParam String password) {
        try {
            // Check if user already exists with the given email
            Optional<User> existingUser = userService.findByEmail(email);
           
            if (existingUser.isPresent()) {
                // User exists, so we return an error message
                model.addAttribute("error", "El email ya está en uso");
                return "signup"; // Return back to the registration form
            }

            userService.createUser(name, email, password, image, "USER");

            return "redirect:/"; // Redirect to homepage or user profile page after successful login

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ha ocurrido un error.");
            return "signup"; // Redirect back to the registration page with an error message
        }
    }

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();

        if (principal == null) {
            return "login";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/user/image/{userId}")
    public ResponseEntity<byte[]> getUserImage(@PathVariable("userId") Long userId) {
        try {
            User user = userService.findById(userId).get(); // Implement this method to find user by ID
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
    public String profile(Model model, HttpServletRequest request) {

        String principal = request.getUserPrincipal().getName();
        Optional<User> user = userService.findByEmail(principal);

        if (user.isPresent()) {
            boolean hasTickets = ticketService.findByUserId(user.get().getId(), PageRequest.of(0, 1)).hasContent();

            model.addAttribute("user", user.get());
            model.addAttribute("hasTickets", hasTickets);

            return "profile";

        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/more-tickets")
    public String ticketsTemplate(Model model, @RequestParam("page") int page, HttpServletRequest request) {

        String principal = request.getUserPrincipal().getName();
        Optional<User> user = userService.findByEmail(principal);
        Page<Ticket> pageQuery = ticketService.findByUserId(user.get().getId(), PageRequest.of(page, 6));

        model.addAttribute("tickets", pageQuery.getContent());
        model.addAttribute("user", user.get());
        boolean hasNext = ticketService.findAllPage(PageRequest.of(page+1, 6)).hasContent();
        model.addAttribute("hasNext", hasNext);

        return "ticket-list";
    }

    @PostMapping("/user/update/name")
    public ResponseEntity<?> updateUserName(@RequestBody Map<String, String> payload, HttpServletRequest request) {
        String principal = request.getUserPrincipal().getName();
        Optional<User> user = userService.findByEmail(principal);
        Long userId = user.get().getId();
        // Extract the new name from the payload
        String newName = payload.get("value");
        User updatedUser = userService.updateUserName(userId, newName);
        if (updatedUser != null) {
            return ResponseEntity.ok().body("User name updated successfully");
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    @PostMapping("/user/update/email")
    public ResponseEntity<?> updateUserEmail(@RequestBody Map<String, String> payload, HttpServletRequest request) {
        String principal = request.getUserPrincipal().getName();
        Optional<User> user = userService.findByEmail(principal);
        Long userId = user.get().getId();
        // Extract the new email from the payload
        String newEmail = payload.get("value");
        User updatedUser = userService.updateUserEmail(userId, newEmail);
        if (updatedUser != null) {
            return ResponseEntity.ok().body("User email updated successfully");
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    @PostMapping("/user/update/image")
    public ResponseEntity<?> updateUserImage(@RequestParam("imageFile") MultipartFile file, HttpServletRequest request) {
        try {
            String principal = request.getUserPrincipal().getName();
            User user = userService.findByEmail(principal).get();

            user.setImg_user(BlobProxy.generateProxy(file.getInputStream(), file.getSize()));
            user.setImage(true);
            userService.save(user);

            return ResponseEntity.ok("Image updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update image");
        }
    }
}
