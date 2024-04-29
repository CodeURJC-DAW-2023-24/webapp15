package es.codeurjc.webapp15.controller.restController;

import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.webapp15.security.jwt.AuthResponse;
import es.codeurjc.webapp15.security.jwt.LoginRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import es.codeurjc.webapp15.security.jwt.UserLoginService;
import es.codeurjc.webapp15.security.jwt.AuthResponse.Status;
import es.codeurjc.webapp15.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api")
public class AuthRestController {
        
        @Autowired
        private UserLoginService userLoginService;

        @Autowired
        private UserService userService;
    
        @Operation(summary = "Log in")
        @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200",
                description = "User logged in successfully",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "403",
                description = "Forbidden",
                content = @Content(mediaType = "application/json")
            )
        })
        @PostMapping(value="/login",consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<AuthResponse> login(
                @CookieValue(name = "accessToken", required = false) String accessToken,
                @CookieValue(name = "refreshToken", required = false) String refreshToken,
                @RequestBody LoginRequest loginRequest,
                HttpServletRequest request) {
            
            return userLoginService.login(loginRequest, accessToken, refreshToken);
        }
        
        @PostMapping("/refresh")
        public ResponseEntity<AuthResponse> refreshToken(
                @CookieValue(name = "refreshToken", required = false) String refreshToken) {
            return userLoginService.refresh(refreshToken);
        }
    
        @Operation(summary = "Log out ")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Logout successful", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class)) })})
        @PostMapping("/logout")
        public ResponseEntity<AuthResponse> logOut(HttpServletRequest request, HttpServletResponse response) {
    
            return ResponseEntity.ok(new AuthResponse(Status.SUCCESS, userLoginService.logout(request, response)));
        }
    
    
}

