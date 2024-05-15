package com.yasiulevichnikita.VacationSolution.services;

import com.yasiulevichnikita.VacationSolution.models.Image;
import com.yasiulevichnikita.VacationSolution.models.User;
import com.yasiulevichnikita.VacationSolution.models.enums.Role;
import com.yasiulevichnikita.VacationSolution.repositories.ImageRepository;
import com.yasiulevichnikita.VacationSolution.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean addUser(User user, MultipartFile file) {
        Image image;
        if(file.getSize() != 0){
            image = toImageEntity(file);
            user.setAvatar(image);
            image.setUser(user);
            log.info("Before");
            log.info("After");
        }
        log.info("Adding new user with email: {}", user.getEmail());
        if (userRepository.findByEmail(user.getEmail()) != null) {
            log.info("User with email: {} already exists", user.getEmail());
            return false;
        }
        user.setActive(true);
        user.getRoles().add(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    private Image toImageEntity(MultipartFile file) {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        try {
            image.setBytes(file.getBytes());
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        return image;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void banUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            if (user.isActive()) {
                user.setActive(false);
                log.info("Ban user with email: {}", user.getEmail());
            } else {
                user.setActive(true);
                log.info("Unban user with email: {}", user.getEmail());
            }
        }
        userRepository.save(user);
    }

    public void changeUserRole(User user, @RequestParam Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }
}
