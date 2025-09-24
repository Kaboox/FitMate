package kabox.fitmate.service;

import kabox.fitmate.Model.Exercise;
import kabox.fitmate.Repository.ExerciseRepository;
import org.springframework.transaction.annotation.Transactional;
import kabox.fitmate.Model.Role;
import kabox.fitmate.Model.User;
import kabox.fitmate.Repository.UserRepository;
import kabox.fitmate.dto.UserLoginRequest;
import kabox.fitmate.dto.UserRegisterRequest;
import kabox.fitmate.dto.UserResponse;
import kabox.fitmate.dto.UserUpdateRequest;
import kabox.fitmate.exception.EmailAlreadyExistsException;
import kabox.fitmate.exception.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ExerciseRepository exerciseRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, ExerciseRepository exerciseRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.exerciseRepository = exerciseRepository;
    }

    public User registerUser(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already in use: " + request.getEmail());
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    public String login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return jwtService.generateToken(user.getEmail());
    }

    @Transactional
    public void updateUserProfile(String email, UserUpdateRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));


        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            user.setUsername(request.getUsername());
        }

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getAvatarUrl() != null && !request.getAvatarUrl().isBlank()) {
            user.setAvatarUrl(request.getAvatarUrl());
        }

        User saved = userRepository.save(user);
        System.out.println("User saved: " + saved.getUsername());
    }

    @Transactional(readOnly = true)
    public UserResponse getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.getFavorites().size();

        return new UserResponse(user);
    }

    @Transactional
    public User addFavorite(Long userId, Long exerciseId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));

        if (user.getFavorites().contains(exercise)) {
            throw new IllegalStateException("Exercise already in favorites");
        }

        user.getFavorites().add(exercise);
        return userRepository.save(user);
    }

    @Transactional
    public User removeFavorite(Long userId, Long exerciseId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));

        if (!user.getFavorites().contains(exercise)) {
            throw new IllegalStateException("Exercise not in favorites");
        }

        user.getFavorites().remove(exercise);
        return userRepository.save(user);
    }




    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
