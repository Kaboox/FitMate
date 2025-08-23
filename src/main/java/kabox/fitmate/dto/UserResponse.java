package kabox.fitmate.dto;

import kabox.fitmate.Model.Role;

public class UserResponse {
    private Long id;
    private String email;
    private String name;
    private Role role;

    public UserResponse(Long id, String email, String name, Role role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getName() { return name; }
    public Role getRole() { return role; }


}
