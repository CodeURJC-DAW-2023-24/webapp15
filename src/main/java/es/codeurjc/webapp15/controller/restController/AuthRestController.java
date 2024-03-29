package es.codeurjc.webapp15.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.webapp15.security.LoginRequest;
import es.codeurjc.webapp15.service.UserService;

@RestController
@RequestMapping("/api")
public class AuthRestController {

    @Autowired
    private UserService userService;
    
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        //TODO: process POST request
        userService.login(loginRequest);
        
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout() {
        //TODO: process POST request        
        
        return ResponseEntity.noContent().build();
    }
}
