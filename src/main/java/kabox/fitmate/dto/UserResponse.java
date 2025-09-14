package kabox.fitmate.dto;

import kabox.fitmate.Model.Role;
import kabox.fitmate.Model.User;

public class UserResponse {
    private Long id;
    private String email;
    private String username;
    private Role role;
    private String avatarUrl;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.role = user.getRole();
        this.avatarUrl = user.getAvatarUrl();
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public Role getRole() { return role; }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
