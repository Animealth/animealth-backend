package animealth.animealthbackend.domain.spending_history;

import animealth.animealthbackend.api.spending_history.dto.SpendingHistoryDTO;
import animealth.animealthbackend.domain.common.BaseEntity;
import animealth.animealthbackend.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 가계부 엔티티
 */
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table
@DynamicUpdate
@Where(clause = "IS_DELETED = false")
public class SpendingHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SPENDINGHISTORTY_ID")
    private Long id;

    @Column(name = "SPENDINGHISTORTY_SPENDINGCONTENT")
    private String spendingContent;

    @Column(name = "SPENDINGHISTORTY_SPENDINGDATE")
    private LocalDate spendingDate;

    @Column(name = "SPENDINGHISTORTY_SPENDINGTYPE")
    private String spendingType;

    @Column(name = "SPENDINGHISTORTY_SPENDINGAMOUNT")
    private BigDecimal spendingAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER")
    private User user;

    public SpendingHistory update(String spendingContent, LocalDate spendingDate, String spendingType, BigDecimal spendingAmount) {
        this.spendingContent = spendingContent;
        this.spendingDate = spendingDate;
        this.spendingType = spendingType;
        this.spendingAmount = spendingAmount;
        return this;
    }

    public static SpendingHistory createSpendingHistory(SpendingHistoryDTO spendingHistoryDTO) {
        return SpendingHistory.builder()
                .spendingContent(spendingHistoryDTO.getSpendingContent())
                .spendingDate(spendingHistoryDTO.getSpendingDate())
                .spendingType(spendingHistoryDTO.getSpendingType())
                .spendingAmount(spendingHistoryDTO.getSpendingAmount())
                .user(spendingHistoryDTO.getUser())
                .build();
    }
}
