package es.codeurjc.webapp15.controller.restController;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.webapp15.model.User;
import es.codeurjc.webapp15.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Logger;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;


//TODO: Not tested because security is not done
@RestController
@RequestMapping("/api/users")
public class UserRestController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody String entity) {
        //TODO: process POST request
        
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@RequestBody String entity) {
        //TODO: process POST request        
        
        return ResponseEntity.notFound().build();
    }
    
    

    @GetMapping("/me")
    public ResponseEntity<Object> me(HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            Optional<User> user = userService.findByEmail(principal.getName());
            if (user.isPresent()) {
                return ResponseEntity.ok(user.get().getId());
            }
        }

        return ResponseEntity.notFound().build();
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable Long id, HttpServletRequest request) {
        
        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            Optional<User> user = userService.findByEmail(principal.getName());
            if (user.isPresent()) {
                if (user.get().isRole("ADMIN") ||
                    user.get().isRole("USER") && user.get().getId() == id) {
                        return ResponseEntity.ok(user);
                    }
            }
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<Object> getUserImage(@PathVariable Long id, HttpServletRequest request) {
        
        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            Optional<User> user = userService.findByEmail(principal.getName());
            if (user.isPresent()) {
                if (user.get().isRole("ADMIN") ||
                    (user.get().isRole("USER") && user.get().getId() == id) &&
                    user.get().getImg_user() != null) {
                        try {
                            byte[] imageBytes = user.get().getImg_user().getBytes(1, (int) user.get().getImg_user().length());
                            HttpHeaders headers = new HttpHeaders();
                            headers.setContentType(MediaType.IMAGE_JPEG); // Or the correct content type of your image
                            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
                        }
                        catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
            }
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<Object> postUserImage(@PathVariable Long id, @RequestParam("imageFile") MultipartFile file, HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            Optional<User> user = userService.findByEmail(principal.getName());
            if (user.isPresent()) {
                if (user.get().isRole("ADMIN") ||
                    (user.get().isRole("USER") && user.get().getId() == id) &&
                    user.get().getImg_user() != null) {
                        try {
                            user.get().setImg_user(BlobProxy.generateProxy(file.getInputStream(), file.getSize()));
                            user.get().setImage(true);
                            userService.save(user.get());

                            URI location = fromCurrentRequest().path("/{id}/image").buildAndExpand(user.get().getId()).toUri();
                            return ResponseEntity.created(location).build();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            }
        }

        return ResponseEntity.notFound().build();
    }
    
    
    

}
