package animealth.animealthbackend.api.user.dto;

import animealth.animealthbackend.domain.user.Role;
import animealth.animealthbackend.domain.user.User;
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

    private Boolean isDeleted;

    @Builder
    public UserDTO(String name, String email, String phone, String nickname, Role role, Boolean isDeleted) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.nickname = nickname;
        this.role = role;
        this.isDeleted = isDeleted;
    }

    public static UserDTO from(User user) {
        return UserDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .nickname(user.getNickname())
                .role(user.getRole())
                .build();
    }
}
