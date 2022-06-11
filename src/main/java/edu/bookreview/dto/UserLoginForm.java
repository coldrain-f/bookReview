package edu.bookreview.dto;

import lombok.Getter;

@Getter
public class UserLoginForm {

    private String username;
    private String password;

    public UserLoginForm(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
