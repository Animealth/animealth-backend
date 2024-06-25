package animealth.animealthbackend.api.spending_history.service;

import animealth.animealthbackend.api.spending_history.dto.SpendingHistoryDTO;
import animealth.animealthbackend.api.user.service.UserService;
import animealth.animealthbackend.domain.spending_history.SpendingHistory;
import animealth.animealthbackend.domain.spending_history.SpendingHistoryRepository;
import animealth.animealthbackend.domain.user.User;
import animealth.animealthbackend.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SpendingHistoryService {
    private final SpendingHistoryRepository spendingHistoryRepository;
    private final UserRepository userRepository;

    //가계부 등록
    @Transactional
    public SpendingHistory save(Long userId, SpendingHistoryDTO spendingHistoryDTO) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );
        SpendingHistory spendingHistory = SpendingHistory.builder()
                .spendingId(spendingHistoryDTO.getSpendingId())
                .spendingAmount(spendingHistoryDTO.getSpendingAmount())
                .user(user)
                .spendingContent(spendingHistoryDTO.getSpendingContent())
                .spendingDate(spendingHistoryDTO.getSpendingDate())
                .spendingType(spendingHistoryDTO.getSpendingType())
                .build();
        return spendingHistoryRepository.save(
                spendingHistory
        );
    }
}
