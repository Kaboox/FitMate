package kabox.fitmate.dto;

import kabox.fitmate.Model.Exercise;
import kabox.fitmate.Model.Role;
import kabox.fitmate.Model.User;
import java.util.Set;
import java.util.stream.Collectors;

public class UserResponse {
    private Long id;
    private String email;
    private String username;
    private Role role;
    private String avatarUrl;
    private Set<Long> favoriteExerciseIds;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.role = user.getRole();
        this.avatarUrl = user.getAvatarUrl();
        this.favoriteExerciseIds = user.getFavoriteExercises().stream()
                .map(Exercise::getId)
                .collect(Collectors.toSet());

    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public Role getRole() { return role; }
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Set<Long> getFavoriteExerciseIds() {
        return favoriteExerciseIds;
    }
}
