package animealth.animealthbackend.global.config.auth.dto;

import animealth.animealthbackend.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable { // 직렬화 기능을 가진 세션 DTO
    //세션에 저장할 인증된 사용자 정보를 담기 위한 클래스
    // 인증된 사용자 정보만 필요 => name, email, phone, nickname 필드만 선언
    private String name;
    private String email;
    private String phone;
    private String nickname;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.nickname = user.getNickname();
    }
}