package kabox.fitmate.Controller;

import jakarta.servlet.http.HttpServletRequest;
import kabox.fitmate.Model.Exercise;
import kabox.fitmate.Model.User;
import kabox.fitmate.Repository.ExerciseRepository;
import kabox.fitmate.Repository.UserRepository;
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
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

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
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User u = userRepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

//        String avatar = u.getAvatarUrl();
//        String full = avatar == null ? null
//                : ServletUriComponentsBuilder.fromRequestUri(request)
//                .replacePath(null)
//                .build()
//                .toUriString() + avatar;

        return ResponseEntity.ok(new UserResponse(u));
    }

    @GetMapping("/me/favorites")
    public ResponseEntity<Set<Long>> getFavorites(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userDetails.getUser();

        Set<Long> favoriteIds = user.getFavorites().stream().map(ex -> ex.getId()).collect(Collectors.toSet());
        return ResponseEntity.ok(favoriteIds);

    }

    @PostMapping("/me/favorites/{exerciseId}")
    public ResponseEntity<?> addFavorites(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long exerciseId) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User updatedUser = userService.addFavorite(userDetails.getUser().getId(), exerciseId);
        return ResponseEntity.ok(new UserResponse(updatedUser));
    }

    @DeleteMapping("/me/favorites/{exerciseId}")
    public ResponseEntity<UserResponse> removeFavorite(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long exerciseId) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User updatedUser = userService.removeFavorite(userDetails.getUser().getId(), exerciseId);
        return ResponseEntity.ok(new UserResponse(updatedUser));
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
    public ResponseEntity<?> updateProfile(
            @RequestBody UserUpdateRequest request,
            @AuthenticationPrincipal(expression = "username") String userEmail) {

        System.out.println("UpdateProfile request: " + request.getUsername() + " | " + request.getPassword() + " | " + request.getAvatarUrl());
        System.out.println("From token email: " + userEmail);

        userService.updateUserProfile(userEmail, request);
        return ResponseEntity.ok(Map.of("message", "Profile updated"));
    }


    @PutMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadAvatar(
            @RequestParam("avatar") MultipartFile file,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        try {
            // 1. Pobieramy ID zalogowanego użytkownika
            Long userId = userDetails.getUser().getId();

            // 2. KLUCZOWE: Pobieramy "żywego" użytkownika z bazy.
            // To naprawia błąd "failed to lazily initialize a collection"
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // 3. Używamy serwisu do zapisu (on ogarnia te ścieżki D:/fitmate/...)
            // Serwis też sam robi save do bazy w środku.
            avatarService.storeAvatar(file, user);

            // 4. Zwracamy odpowiedź używając "żywego" obiektu user
            return ResponseEntity.ok(new UserResponse(user));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (IOException e) {
            e.printStackTrace(); // Warto widzieć błąd w konsoli
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Could not upload file"));
        }
    }

}
