package animealth.animealthbackend.global.config.auth.dto;

import animealth.animealthbackend.domain.user.Role;
import animealth.animealthbackend.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import java.util.Map;

@Getter
public class OAuthAttributes {
    //OAuth2 인증 과정에서 반환된 사용자 정보를 다루기 위한 클래스
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String phone;
    private String nickname;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributeKey, String name,
                           String email, String nickname, String phone) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.phone = phone;
    }

    // OAuth2User에서 반환하는 사용자 정보는 Map
    // 따라서 값 하나하나를 변환해야 한다.
    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes) {

        return ofGoogle(userNameAttributeName, attributes);
    }

    // 구글 생성자
    private static OAuthAttributes ofGoogle(String usernameAttributeName,
                                            Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .phone((String) attributes.get("phone"))
                .nickname((String) attributes.get("nickname"))
                .attributes(attributes)
                .nameAttributeKey(usernameAttributeName)
                .build();
    }

    // User 엔티티 생성
    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .nickname(nickname)
                .phone(phone)
                .role(Role.USER)
                .build();
    }
}
