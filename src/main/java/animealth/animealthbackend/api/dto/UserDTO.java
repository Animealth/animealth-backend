package animealth.animealthbackend.api.dto;

import animealth.animealthbackend.domain.user.Role;
import animealth.animealthbackend.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDTO {
    private String name;

    private String email;

    private String phone;

    private String nickname;

    private Role role;

    @Builder
    public UserDTO(String name, String email, String phone, String nickname, Role role) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.nickname = nickname;
        this.role = role;
    }
}
