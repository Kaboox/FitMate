package kabox.fitmate.Controller;

import jakarta.servlet.http.HttpServletRequest;
import kabox.fitmate.Model.User;
import kabox.fitmate.Repository.UserRepository;
import kabox.fitmate.dto.UserRegisterRequest;
import kabox.fitmate.dto.UserResponse;
import kabox.fitmate.dto.UserUpdateRequest;
import kabox.fitmate.security.CustomUserDetails;
import kabox.fitmate.service.AvatarService;
import kabox.fitmate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    private final AvatarService avatarService;

    @Autowired
    public UserController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }


    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal CustomUserDetails userDetails,
                                           HttpServletRequest request) {
        if (userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        User u = userDetails.getUser();
        String avatar = u.getAvatarUrl();
        String full = avatar == null ? null
                : ServletUriComponentsBuilder.fromRequestUri(request).replacePath(null).build().toUriString() + avatar;
        return ResponseEntity.ok(new UserResponse(u));
    }

    @GetMapping("/{id}/avatar")
    public ResponseEntity<String> getAvatar(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow();

        if (user.getAvatarUrl() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(user.getAvatarUrl());
    }






    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody UserUpdateRequest request,
                                           @AuthenticationPrincipal String userEmail) {
        userService.updateUserProfile(userEmail, request);
        return ResponseEntity.ok(Map.of("message", "Profile updated"));
    }

    @PutMapping("/avatar")
    public ResponseEntity<UserResponse> uploadAvatar(
            @RequestParam("avatar") MultipartFile file,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        try {
            // Unique file name
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

            // Catalog path
            Path uploadPath = Paths.get("uploads");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save file
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Create URL
            String fileUrl = "http://localhost:8080/uploads/" + filename;

            // Save in db
            User user = userDetails.getUser();
            user.setAvatarUrl(fileUrl);
            userRepository.save(user);

            return ResponseEntity.ok(
                    new UserResponse(user)
            );

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
