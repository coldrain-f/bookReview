package edu.bookreview.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public class UserDto {

    private final String username;
    private final String nickname;
}
