package animealth.animealthbackend.api.spending_history.service;

import animealth.animealthbackend.api.spending_history.dto.SpendingHistoryDTO;
import animealth.animealthbackend.domain.spending_history.SpendingHistory;
import animealth.animealthbackend.domain.spending_history.SpendingHistoryRepository;
import animealth.animealthbackend.domain.user.User;
import animealth.animealthbackend.domain.user.UserRepository;
import animealth.animealthbackend.dummy.DummySpendingHistory;
import animealth.animealthbackend.dummy.DummyUser;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static animealth.animealthbackend.domain.user.Role.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpendingHistoryServiceTest {

    @Mock
    private SpendingHistoryRepository spendingHistoryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SpendingHistoryService spendingHistoryService;

    private User user;
    private SpendingHistoryDTO spendingHistoryDTO;
    private SpendingHistory spendingHistory;

    @BeforeEach
    void setUp() {
        user = DummyUser.createDummyUser("user", "user@example.com", "01012341234", "뽀미엄마", USER);
        spendingHistory = DummySpendingHistory.createDummySpendingHistory(user);
        spendingHistoryDTO = DummySpendingHistory.createDummySpendingHistoryDTO(user);
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = {"USER"})
    void save() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(spendingHistoryRepository.save(any(SpendingHistory.class))).thenReturn(spendingHistory);

        SpendingHistoryDTO result = spendingHistoryService.save(1L, spendingHistoryDTO);

        assertNotNull(result);
        assertEquals(spendingHistoryDTO.getSpendingContent(), result.getSpendingContent());
        verify(userRepository, times(1)).findById(1L);
        verify(spendingHistoryRepository, times(1)).save(any(SpendingHistory.class));
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = {"USER"})
    void findById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(spendingHistoryRepository.findByUserId(1L)).thenReturn(Collections.singletonList(spendingHistory));

        List<SpendingHistoryDTO> result = spendingHistoryService.findById(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(spendingHistoryDTO.getSpendingContent(), result.get(0).getSpendingContent());
        verify(userRepository, times(1)).findById(1L);
        verify(spendingHistoryRepository, times(1)).findByUserId(1L);
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = {"USER"})
    void update() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(spendingHistoryRepository.findById(1L)).thenReturn(Optional.of(spendingHistory));

        SpendingHistoryDTO updatedSpendingHistoryDTO = DummySpendingHistory.updateDummySpendingHistoryDTO(user);
        SpendingHistoryDTO result = spendingHistoryService.update(1L, updatedSpendingHistoryDTO);

        assertNotNull(result);
        assertEquals(updatedSpendingHistoryDTO.getSpendingContent(), result.getSpendingContent());
        verify(userRepository, times(1)).findById(1L);
        verify(spendingHistoryRepository, times(1)).findById(1L);
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = {"USER"})
    void delete() {
        // spendingHistoryService.delete()에서 userId와 전달받은 id가 일치하는지 확인하므로 userId를 설정해줘야함
        user = DummyUser.deleteDummyUser(1L, "user", "user@example.com", "01012341234", "뽀미엄마", USER);
        spendingHistory = DummySpendingHistory.createDummySpendingHistory(user);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(spendingHistoryRepository.findById(1L)).thenReturn(Optional.of(spendingHistory));


        boolean result = spendingHistoryService.delete(1L, 1L);

        assertTrue(result);
        verify(userRepository, times(1)).findById(1L);
        verify(spendingHistoryRepository, times(1)).findById(1L);
        verify(spendingHistoryRepository, times(1)).delete(spendingHistory);
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = {"USER"})
    void testUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            spendingHistoryService.save(1L, spendingHistoryDTO);
        });

        assertEquals("User not found", thrown.getMessage());
        verify(userRepository, times(1)).findById(1L);
        verify(spendingHistoryRepository, times(0)).save(any(SpendingHistory.class));
    }
}