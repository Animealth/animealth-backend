package animealth.animealthbackend.api.service;

import animealth.animealthbackend.api.user.dto.UserDTO;
import animealth.animealthbackend.api.user.service.UserService;
import animealth.animealthbackend.domain.user.User;
import animealth.animealthbackend.domain.user.UserRepository;
import animealth.animealthbackend.dummy.DummyUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static animealth.animealthbackend.domain.user.Role.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user1, user2;

    @BeforeEach
    void setUp() {
        user1 = DummyUser.createDummyUser("user1", "user1@example.com", "01012341234", "뽀미엄마", USER);
        user2 = DummyUser.createDummyUser("user2", "user2@example.com", "01098765432", "뽀미아빠", USER);
    }

    @Test
    void findAll() {
        List<User> userList = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(userList);

        List<UserDTO> userDTOList = userService.findAll();

        assertEquals(2, userDTOList.size());
    }

    @Test
    void findById_ExistingUser() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user1));

        UserDTO userDTO = userService.findById(userId);

        assertEquals(user1.getName(), userDTO.getName());
    }

    @Test
    void findById_NonExistingUser() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.findById(userId));
    }

    @Test
    void update_ExistingUser() {
        Long userId = 2L;
        UserDTO updateUserDTO = UserDTO.builder()
                .name("user02")
                .phone("01011111111")
                .nickname("뽀미삼촌")
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user2));

        UserDTO result = userService.update(userId, updateUserDTO);

        assertEquals(updateUserDTO.getName(), result.getName());
        assertEquals(updateUserDTO.getPhone(), result.getPhone());
        assertEquals(updateUserDTO.getNickname(), result.getNickname());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void update_NonExistingUser() {
        Long userId = 2L;
        UserDTO updateUserDTO = UserDTO.builder().build();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.update(userId, updateUserDTO));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void delete_ExistingUser() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user1));

        UserDTO result = userService.delete(userId);

        assertEquals(user1.getName(), result.getName());
        assertTrue(result.getIsDeleted());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void delete_NonExistingUser() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.delete(userId));
        verify(userRepository, never()).save(any(User.class));
    }
}