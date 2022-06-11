package edu.bookreview.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginForm {

    public String username;
    public String password;

    public UserLoginForm(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
