package animealth.animealthbackend.domain.spending_history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpendingHistoryRepository extends JpaRepository<SpendingHistory, Long> {
    //userId로 spendinghistory 찾기
    @Query("select sh from SpendingHistory sh where sh.user.userId = :userId")
    List<SpendingHistory> findByUserId(Long userId);
}
