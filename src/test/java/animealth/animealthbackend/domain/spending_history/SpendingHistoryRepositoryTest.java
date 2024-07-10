package animealth.animealthbackend.domain.spending_history;

import animealth.animealthbackend.domain.user.Role;
import animealth.animealthbackend.domain.user.User;
import animealth.animealthbackend.dummy.DummySpendingHistory;
import animealth.animealthbackend.dummy.DummyUser;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static animealth.animealthbackend.domain.user.Role.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpendingHistoryRepositoryTest {

    @Mock
    private SpendingHistoryRepository spendingHistoryRepository;

    @Test
    void testFindByUserId_ExistingUserId() {
        // Given
        User user = DummyUser.deleteDummyUser(1L, "user", "user@example.com", "01012341234", "뽀미엄마", USER);
        SpendingHistory spendingHistory = DummySpendingHistory.createDummySpendingHistory(user);
        Long userId = 1L;
        when(spendingHistoryRepository.findByUserId(userId)).thenReturn(Collections.singletonList(spendingHistory));

        // When
        List<SpendingHistory> result = spendingHistoryRepository.findByUserId(userId);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("지출", result.get(0).getSpendingType());
        assertEquals("간식", result.get(0).getSpendingContent());
    }

    @Test
    void testFindByUserId_NonExistingUserId() {
        // Given
        Long userId = 999L;
        when(spendingHistoryRepository.findByUserId(userId)).thenReturn(Collections.emptyList());

        // When
        List<SpendingHistory> result = spendingHistoryRepository.findByUserId(userId);

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByUserId_NullUserId() {
        // Given
        Long userId = null;
        when(spendingHistoryRepository.findByUserId(userId)).thenThrow(IllegalArgumentException.class);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> spendingHistoryRepository.findByUserId(userId));
    }
}