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

    @Column(columnDefinition = "bit default false NOT NULL COMMENT '이용가능여부'")
    private Boolean isDeleted;


    @Builder
    public User(String name, String email, String phone, String nickname, Role role) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.nickname = nickname;
        this.role = role;
        this.isDeleted = false;
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
