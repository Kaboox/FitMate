package kabox.fitmate.Controller;

import kabox.fitmate.Model.User;
import kabox.fitmate.Repository.UserRepository;
import kabox.fitmate.dto.UserRegisterRequest;
import kabox.fitmate.dto.UserResponse;
import kabox.fitmate.dto.UserUpdateRequest;
import kabox.fitmate.security.CustomUserDetails;
import kabox.fitmate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


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
    public ResponseEntity<UserResponse> getProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userDetails.getUser();

        UserResponse response = new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getRole(),
                user.getAvatarUrl()
        );

        return ResponseEntity.ok(response);
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

}
