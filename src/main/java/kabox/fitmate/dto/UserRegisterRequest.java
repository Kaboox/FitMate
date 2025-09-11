package kabox.fitmate.dto;

import kabox.fitmate.Model.Role;

public class UserRegisterRequest {
    private String email;
    private String password;
    private String username;


    public UserRegisterRequest() {}

    public void setPassword(String password) {
        this.password = password;
    }



    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.username = name;
    }

    public String getEmail() { return email; }
    public String getName() { return username; }

    public String getPassword() {
        return password;
    }
}
