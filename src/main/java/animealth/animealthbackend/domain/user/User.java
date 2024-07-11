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
/**
 * AllArgsConstructor 가 필요한 이유?? 테스트 코드 때문인가요? -> 아이디 값 포함 생성한 생성자
 * @Where(clause = "IS_DELETED = false") 필요 -> soft 딜리트용!
 */
@AllArgsConstructor
@DynamicUpdate
public class User extends BaseEntity {

    /**
     * @Column(name = "USER_ID") 로 두고
     * 그냥 id로 두는거 어떤지?
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId; // id

    @Column(nullable = false, name = "USER_NAME")
    private String name;

    @Column(nullable = false)
    private String email;

    private String phone;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * BaseEntity를 상속 받았기 때문에 필요없다고 생각합니다. / Oauth 땜에 필요한건지??
     */
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
