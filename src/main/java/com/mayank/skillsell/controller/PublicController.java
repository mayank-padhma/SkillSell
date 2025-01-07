package com.mayank.skillsell.controller;

import com.mayank.skillsell.dto_and_mapper.NewUserDto;
import com.mayank.skillsell.service.UserDetailsServiceImpl;
import com.mayank.skillsell.service.UserService;
import com.mayank.skillsell.utils.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/public")
@Slf4j
@Tag(name = "Public APIs", description = "Handles user signup and authentications")
public class PublicController {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "Ok";
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody NewUserDto newUserDto) {
        try {
            userService.createUser(newUserDto);
            return ResponseEntity.ok("User registered successfully");
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/login/{username}/{password}")
    public ResponseEntity<String> login(
            @PathVariable String username,
            @PathVariable String password
    ){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        try {
            // Use the service to verify the user by the token
            userService.verifyUserByToken(token);

            return ResponseEntity.ok("Email verified successfully!");
        } catch (RuntimeException e) {
            // Handle the case where the user is not found or other errors
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
