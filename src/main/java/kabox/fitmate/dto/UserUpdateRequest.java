package kabox.fitmate.dto;

import kabox.fitmate.Model.Role;

public class UserUpdateRequest {
    private String password;
    private String username;
    private String avatarUrl;


    public UserUpdateRequest() {}

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() { return username; }

    public String getPassword() {
        return password;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
