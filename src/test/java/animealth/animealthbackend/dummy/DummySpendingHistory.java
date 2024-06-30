package animealth.animealthbackend.dummy;

import animealth.animealthbackend.api.spending_history.dto.SpendingHistoryDTO;
import animealth.animealthbackend.domain.spending_history.SpendingHistory;
import animealth.animealthbackend.domain.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 테스트에 사용할 가계부 더미 클래스
 */
public class DummySpendingHistory {

    public static SpendingHistory createDummySpendingHistory(User user){
        return SpendingHistory.builder()
                .spendingType("지출")
                .spendingContent("간식")
                .spendingAmount(BigDecimal.valueOf(3000))
                .spendingDate(LocalDate.parse("2024-06-12"))
                .user(user)
                .build();
    }

    public static SpendingHistoryDTO createDummySpendingHistoryDTO(User user){
        return SpendingHistoryDTO.builder()
                .spendingId(1L)
                .spendingType("지출")
                .spendingContent("간식")
                .spendingAmount(BigDecimal.valueOf(3000))
                .spendingDate(LocalDate.parse("2024-06-12"))
                .user(user)
                .build();
    }

    public static SpendingHistoryDTO updateDummySpendingHistoryDTO(User user){
        return SpendingHistoryDTO.builder()
                .spendingId(1L)
                .spendingType("지출")
                .spendingContent("미용")
                .spendingAmount(BigDecimal.valueOf(3000))
                .spendingDate(LocalDate.parse("2024-06-12"))
                .user(user)
                .build();
    }

}
