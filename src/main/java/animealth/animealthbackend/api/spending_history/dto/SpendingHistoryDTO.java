package animealth.animealthbackend.api.spending_history.dto;

import animealth.animealthbackend.domain.spending_history.SpendingHistory;
import animealth.animealthbackend.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 가계부 DTO
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpendingHistoryDTO {
    private Long spendingId;
    private String spendingContent;
    private LocalDate spendingDate;
    private String spendingType;
    private BigDecimal spendingAmount;
    private User user;

    public static SpendingHistoryDTO from(SpendingHistory spendingHistory) {
        return SpendingHistoryDTO.builder()
                .spendingId(spendingHistory.getSpendingId())
                .spendingContent(spendingHistory.getSpendingContent())
                .spendingDate(spendingHistory.getSpendingDate())
                .spendingType(spendingHistory.getSpendingType())
                .spendingAmount(spendingHistory.getSpendingAmount())
                .user(spendingHistory.getUser())
                .build();
    }
}
