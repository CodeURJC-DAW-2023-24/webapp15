package es.codeurjc.webapp15.controller.restController;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.webapp15.model.Artist;
import es.codeurjc.webapp15.model.User;
import es.codeurjc.webapp15.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.sql.SQLException;
import java.util.Optional;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;



@RestController
@RequestMapping("/api/users")
public class UserRestController {
    
    @Autowired
    private UserService userService;   
    
    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @PutMapping("/{id}")
    public ResponseEntity<User> putUser(@PathVariable Long id, @RequestBody User updatedUser) throws SQLException {

        if (userService.exist(id)) {
            
            // Mantain current image
            User storedUser = userService.findById(id).get();
            if (storedUser.getImg_user() != null) {
                updatedUser.setImg_user(BlobProxy.generateProxy(storedUser.getImg_user().getBinaryStream(), storedUser.getImg_user().length()));
            }

            updatedUser.setId(id);
            userService.save(updatedUser);

            return new ResponseEntity<>(updatedUser, HttpStatus.OK);

        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/newname")
    public ResponseEntity<User> changeInfo(HttpServletRequest request, @RequestBody String name) throws SQLException {
        if(name==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            User user = userService.findByEmail(principal.getName()).get();
            if((user.getName() != name)){
                    user.setName(name);
                    userService.save(user);
                    return new ResponseEntity<>(user,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/newImage")
    public ResponseEntity<User> changeInfo(HttpServletRequest request, @RequestBody MultipartFile image) throws SQLException {
        if(image==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            User user = userService.findByEmail(principal.getName()).get();
            if((user.getImg_user() != image)){
                try {
                    user.setImg_user(BlobProxy.generateProxy(image.getInputStream(), image.getSize()));
                    user.setImage(true);
                    userService.save(user);

                    
                    return new ResponseEntity<>(user,HttpStatus.OK);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Artist> deleteUser(@PathVariable Long id) {

        try {
            userService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Operation (summary = "Registers a new user")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "User registered correctly",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request, maybe one of the user attributes is missing or the type is not valid"
        ),
        @ApiResponse(
            responseCode = "409",
            description = "User already exists"
        )
    })

    @PostMapping("/register")
    public ResponseEntity<User> createUser(HttpServletRequest httpServletRequest, @RequestBody User newUser){
        if((newUser.getEmail() == null) || (newUser.getEncodedPassword()==null) ||(newUser.getName()==null)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(userService.existEmail(newUser.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            newUser.setEncodedPassword(passwordEncoder.encode(newUser.getEncodedPassword()));
            userService.save(newUser);
            URI location = fromCurrentRequest().build().toUri();
            return ResponseEntity.created(location).build();
        }
    }
}
