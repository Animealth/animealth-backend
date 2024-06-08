package animealth.animealthbackend.domain.user;

import animealth.animealthbackend.api.dto.UserDTO;
import animealth.animealthbackend.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigInteger;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private String phone;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    //DTO를 Entity로 변환
    public static User toEntity(UserDTO userDTO) {
        User user = new User();
        user.userId = userDTO.getUserId();
        user.name = userDTO.getName();
        user.email = userDTO.getEmail();
        user.phone = userDTO.getPhone();
        user.nickname = userDTO.getNickname();
        user.role = userDTO.getRole();
        return user;
    }

    public User update(String name, String phone, String nickname) {
        this.name = name;
        this.phone = phone;
        this.nickname = nickname;
        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}
