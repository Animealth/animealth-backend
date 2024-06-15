package animealth.animealthbackend.domain.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import java.util.Optional;

import static animealth.animealthbackend.domain.user.Role.USER;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @Test
    void findByEmail_ExistingEmail_ReturnsUser(){
        //given
        String existingEmail = "test@email.com";
        User user = User.builder()
                .name("user1")
                .email(existingEmail)
                .phone("01012341234")
                .nickname("캔따개")
                .role(USER)
                .build();

        when(userRepository.findByEmail(existingEmail)).thenReturn(Optional.of(user));

        //when
        Optional<User> result = userRepository.findByEmail(existingEmail);

        //then
        assertEquals(user.getEmail(), result.orElseThrow().getEmail());
        assertEquals(false, user.getIsDeleted());

    }

    @Test
    void findByEmail_NonExistingEmail_ReturnsEmptyOptional() {
        //given
        String nonExistingEmail = "test@email.com";

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        //when
        Optional<User> result = userRepository.findByEmail(nonExistingEmail);

        //then
        assertEquals(Optional.empty(), result);
    }
}