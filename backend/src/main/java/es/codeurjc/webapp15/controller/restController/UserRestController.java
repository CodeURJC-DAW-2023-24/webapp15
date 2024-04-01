package es.codeurjc.webapp15.controller.restController;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.webapp15.model.User;
import es.codeurjc.webapp15.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

class NewUser {
    public String email;
    public String name;
    public String password;
}

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    
    @Autowired
    private UserService userService;   
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation (summary = "Gets the logged user")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the user",
            content = {@Content(
                mediaType = "application/json",
                schema = @Schema(implementation=User.class)
                )}
        ),
        @ApiResponse(
            responseCode = "401",
            description = "User not authorized",
            content = @Content
        ),
    })
    @GetMapping("/me")
    public ResponseEntity<Object> me(HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<User> user = userService.findByEmail(principal.getName());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(user);
    }
    
    @Operation (summary = "Gets a user by its id")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the user",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid id supplied",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "401",
            description = "User not authorized",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "User not found or doesn't have permission to access it",
            content = @Content
        ),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(
        @Parameter(description = "id of the user to be searched")
        @PathVariable Long id, HttpServletRequest request) {
        
        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            Optional<User> user = userService.findByEmail(principal.getName());
            if (user.isPresent()) {
                if (user.get().isRole("ADMIN") ||
                    user.get().isRole("USER") && user.get().getId() == id) {
                        Optional<User> requestedUser = userService.findById(id);
                        if (requestedUser.isPresent()) {
                            return ResponseEntity.ok(userService.findById(id));
                        }
                    }
            }
        }

        return ResponseEntity.notFound().build();
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
            description = "Bad request, maybe one of the user attributes is missing or the type is not valid",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "409",
            description = "User already exists",
            content = @Content
        )
    })
    @PostMapping("")
    public ResponseEntity<User> createUser(HttpServletRequest httpServletRequest, @RequestBody NewUser userBody){
        if((userBody.email == null) || (userBody.name == null) || (userBody.password == null)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(userService.existEmail(userBody.email)){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            String encodedPassword = passwordEncoder.encode(userBody.password);
            User newUser = new User(userBody.email, userBody.name, encodedPassword, "USER");
            userService.save(newUser);

            String id = Long.toString(newUser.getId());
            URI uri = fromCurrentRequest().path("/").build().toUri().resolve(id);
            return ResponseEntity.created(uri).build();
        }
    }

    @Operation (summary = "Gets the image of a user by its id")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the user image",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid id supplied",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "401",
            description = "User not authorized",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "User not found, user image not found or doesn't have permission to access it",
            content = @Content
        ),
    })
    @GetMapping("/{id}/image")
    public ResponseEntity<Object> getUserImage(@PathVariable Long id, HttpServletRequest request) throws SQLException {

        Principal principal = request.getUserPrincipal();
        if (principal == null) {
            return ResponseEntity.notFound().build();
        }

        Optional<User> user = userService.findByEmail(principal.getName());
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (user.get().getImg_user() == null) {
            return ResponseEntity.notFound().build();
        }

        if (user.get().isRole("ADMIN") ||
            user.get().isRole("USER") && user.get().getId() == id) {

                Resource file = new InputStreamResource(user.get().getImg_user().getBinaryStream());

                return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .contentLength(user.get().getImg_user().length())
                .body(file);
        }

        return ResponseEntity.notFound().build();
    }

    @Operation (summary = "Updates the image of a user")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Image created correctly",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "204",
            description = "Image updated correctly",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "401",
            description = "User not authorized",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "403",
            description = "User not authorized",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = @Content
        )
    })
    @PutMapping("/{id}/image")
    public ResponseEntity<Object> postUserImage(@PathVariable Long id, @RequestParam("imageFile") MultipartFile file, HttpServletRequest request) throws IOException {

        Principal principal = request.getUserPrincipal();
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<User> user = userService.findByEmail(principal.getName());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (!userService.exist(id)) {
            return ResponseEntity.notFound().build();
        }

        if (user.get().isRole("ADMIN") ||
            user.get().isRole("USER") && user.get().getId() == id) {

                boolean hasImage = user.get().getImg_user() != null;

                user.get().setImg_user(BlobProxy.generateProxy(file.getInputStream(), file.getSize()));
                user.get().setImage(true);
                userService.save(user.get());

                URI location = fromCurrentRequest().buildAndExpand(user.get().getId()).toUri();

                if (!hasImage) {
                    return ResponseEntity.created(location).build();
                }

                return ResponseEntity.noContent().header("Location", location.toString()).build();
        }
        
        return ResponseEntity.notFound().build();

    }

    @Operation(summary = "Update a user by its id")
    @ApiResponses(value = {
    @ApiResponse(
    responseCode = "200",
    description = "User updated correctly",
    content = {@Content(
    mediaType = "application/json",
    schema = @Schema(implementation=User.class)
    )}
    ),
    @ApiResponse(
    responseCode = "400",
    description = "User not updated",
    content = @Content
    ),
    @ApiResponse(
    responseCode = "403",
    description = "User not authorized",
    content = @Content
    )
    })
    @PutMapping("/{id}")
    public ResponseEntity<User> putUser(@PathVariable Long id, @RequestBody NewUser userBody) throws SQLException {

        if (!userService.exist(id)) {
            return ResponseEntity.notFound().build();
        }

        User storedUser = userService.findById(id).get();
        String[] roles = storedUser.getRoles().toArray(new String[0]);

        String encodedPassword = passwordEncoder.encode(userBody.password);
        User updatedUser = new User(userBody.email, userBody.name, encodedPassword, roles);
            
        // Mantain current image
        if (storedUser.getImg_user() != null) {
            updatedUser.setImg_user(BlobProxy.generateProxy(storedUser.getImg_user().getBinaryStream(), storedUser.getImg_user().length()));
        }

        updatedUser.setId(id);
        userService.save(updatedUser);

        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Delete a user by its id")
    @ApiResponses(value = {
    @ApiResponse(
    responseCode = "200",
    description = "User deleted correctly",
    content = {@Content(
    mediaType = "application/json",
    schema = @Schema(implementation=User.class)
    )}
    ),
    @ApiResponse(
    responseCode = "400",
    description = "User not deleted",
    content = @Content
    ),
    @ApiResponse(
    responseCode = "403",
    description = "User not authorized",
    content = @Content
    ),
    @ApiResponse(
    responseCode = "404",
    description = "User not found",
    content = @Content
    )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id, HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<User> user = userService.findByEmail(principal.getName());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (!user.get().isRole("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<User> requestedUser = userService.findById(id);
        if (requestedUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        userService.delete(id);
        return ResponseEntity.ok(requestedUser.get());
    }
}
