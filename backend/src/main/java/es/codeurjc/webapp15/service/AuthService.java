package es.codeurjc.webapp15.service;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.webapp15.model.User;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthService {

    @Autowired
    private UserService userService;
    
    public boolean sessionIsRole(String role, HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();
        if (principal == null) {
            return false;
        }

        Optional<User> user = userService.findByEmail(principal.getName());
        if (user.isEmpty()) {
            return false;
        }

        return (user.get().isRole(role));
    }
}
