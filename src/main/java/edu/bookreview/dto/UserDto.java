package edu.bookreview.dto;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@AllArgsConstructor
public class UserDto {

    private final String username;
    private final String nickname;
}
