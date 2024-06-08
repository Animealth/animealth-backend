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
@AllArgsConstructor
@Builder
@Getter
public class UserDTO {

    private Long userId;

    private String name;

    private String email;

    private String phone;

    private String nickname;

    private Role role;

    public static UserDTO fromEntity(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.userId = user.getUserId();
        userDTO.name = user.getName();
        userDTO.email = user.getEmail();
        userDTO.phone = user.getPhone();
        userDTO.nickname = user.getNickname();
        userDTO.role = user.getRole();
        return userDTO;
    }
}
