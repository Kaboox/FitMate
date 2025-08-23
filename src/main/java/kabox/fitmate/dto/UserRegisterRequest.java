package kabox.fitmate.dto;

import kabox.fitmate.Model.Role;

public class UserRegisterRequest {
    private String email;
    private String password;
    private String name;


    public void setPassword(String password) {
        this.password = password;
    }



    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() { return email; }
    public String getName() { return name; }

    public String getPassword() {
        return password;
    }
}
