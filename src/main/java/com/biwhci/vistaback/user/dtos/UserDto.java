package com.biwhci.vistaback.user.dtos;

import com.biwhci.vistaback.user.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String nickname;
    private String email;

    public UserDto(User user) {
        this.id = user.getUserId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
    }
}
