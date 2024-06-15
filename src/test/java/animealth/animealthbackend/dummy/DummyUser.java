package animealth.animealthbackend.dummy;

import animealth.animealthbackend.domain.user.Role;
import animealth.animealthbackend.domain.user.User;

public class DummyUser {
    public static User createDummyUser(String name, String email, String phone, String nickname, Role role) {
        return new User(name, email, phone, nickname, role);
    }
}

