package animealth.animealthbackend.domain.user;

import animealth.animealthbackend.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "IS_DELETED = false")
@DynamicUpdate
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(nullable = false, name = "USER_NAME")
    private String name;

    @Column(nullable = false, name = "USER_EMAIL")
    private String email;

    @Column(name = "USER_PHONE")
    private String phone;

    @Column(name = "USER_NICKNAME")
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;


    @Builder
    public User(String name, String email, String phone, String nickname, Role role) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.nickname = nickname;
        this.role = role;
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
