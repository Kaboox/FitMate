package kabox.fitmate.dto;

import kabox.fitmate.Model.Role;

public class UserResponse {
    private Long id;
    private String email;
    private String username;
    private Role role;
    private String avatarUrl;

    public UserResponse(Long id, String email, String name, Role role, String avatarUrl) {
        this.id = id;
        this.email = email;
        this.username = name;
        this.role = role;
        this.avatarUrl = avatarUrl;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public Role getRole() { return role; }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
