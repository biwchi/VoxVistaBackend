package com.biwhci.vistaback.user.dtos;

import com.biwhci.vistaback.user.models.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AppUserDto {
    private Integer id;
    private String nickname;
    private String email;

    public AppUserDto(AppUser user) {
        this.id = user.getUserId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
    }
}
