package com.mayank.skillsell.service;

import com.mayank.skillsell.dto_and_mapper.NewUserDto;
import com.mayank.skillsell.dto_and_mapper.UserDto;
import com.mayank.skillsell.dto_and_mapper.UserMapper;
import com.mayank.skillsell.entity.User;
import com.mayank.skillsell.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EmailService emailService;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public UserDto getUserDtoById(Long id) {
        return userMapper.toUserDto(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id)));
    }

    public void createUser(NewUserDto newUserDto) {
        if (userRepository.existsByEmail(newUserDto.email())) {
            throw new RuntimeException("Email already exists!");
        }
        if (userRepository.existsByUsername(newUserDto.username())) {
            throw new RuntimeException("Username already exists!");
        }
        String token = UUID.randomUUID().toString();
        User user = new User();
        user.setUsername(newUserDto.username());
        user.setPassword(passwordEncoder.encode(newUserDto.password()));
        user.setEmail(newUserDto.email());
        user.setPhoneNo(newUserDto.phoneNo());
        user.setRoles(newUserDto.roles());
        user.setIsVerified(false); // Set initial verification state
        user.setVerificationToken(token);
        userRepository.save(user);
        sendVerificationEmail(user);
    }

    private void sendVerificationEmail(User user) {
        String subject = "Verify Your Email Address";
        String verificationUrl = "http://localhost:8080/public/verify?token=" + user.getVerificationToken();
        String message = "Welcome to SkillSell. Click the link to verify your email: " + verificationUrl;

        emailService.sendEmail(
                user.getEmail(),
                subject,
                message
        );
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        User existingUser = getUserById(id);
        existingUser.setUsername(userDto.username());
        existingUser.setRoles(userDto.roles());
        return userMapper.toUserDto(userRepository.save(existingUser));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void verifyUserByToken(String token) {
        // Find user by verification token
        User user = findByVerificationToken(token);

        // Verify the user's email
        user.setIsVerified(true);
        user.setVerificationToken(null); // Clear the token after verification
        userRepository.save(user);
    }

    public User findByVerificationToken(String token) {
        return userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new RuntimeException("User not found with provided verification token")
                );
    }
}