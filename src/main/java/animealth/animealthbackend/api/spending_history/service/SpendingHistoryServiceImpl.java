package animealth.animealthbackend.api.spending_history.service;

import animealth.animealthbackend.api.spending_history.dto.SpendingHistoryDTO;
import animealth.animealthbackend.domain.spending_history.SpendingHistory;
import animealth.animealthbackend.domain.spending_history.SpendingHistoryRepository;
import animealth.animealthbackend.domain.user.User;
import animealth.animealthbackend.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class SpendingHistoryServiceImpl implements SpendingHistoryService {
    private final SpendingHistoryRepository spendingHistoryRepository;
    private final UserRepository userRepository;

    @Override
    public SpendingHistoryDTO save(Long userId, SpendingHistoryDTO spendingHistoryDTO) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );
        SpendingHistory spendingHistory = SpendingHistory.builder()
                .id(spendingHistoryDTO.getSpendingId())
                .spendingAmount(spendingHistoryDTO.getSpendingAmount())
                .user(user)
                .spendingContent(spendingHistoryDTO.getSpendingContent())
                .spendingDate(spendingHistoryDTO.getSpendingDate())
                .spendingType(spendingHistoryDTO.getSpendingType())
                .build();
        return spendingHistoryDTO.from(spendingHistoryRepository.save(
                spendingHistory
        ));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpendingHistoryDTO> findById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );
        List<SpendingHistory> spendingHistories = spendingHistoryRepository.findByUserId(userId);
        return spendingHistories.stream()
                .map(SpendingHistoryDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    public SpendingHistoryDTO update(Long userId, SpendingHistoryDTO spendingHistoryDTO) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );
        Optional<SpendingHistory> optionalSpendingHistory = spendingHistoryRepository.findById(spendingHistoryDTO.getSpendingId());
        if(optionalSpendingHistory.isPresent()){
            SpendingHistory sh = optionalSpendingHistory.get();
            sh = sh.update(spendingHistoryDTO.getSpendingContent(), spendingHistoryDTO.getSpendingDate(), spendingHistoryDTO.getSpendingType(), spendingHistoryDTO.getSpendingAmount());
            spendingHistoryRepository.save(sh);
            return SpendingHistoryDTO.builder()
                    .spendingId(spendingHistoryDTO.getSpendingId())
                    .spendingAmount(sh.getSpendingAmount())
                    .spendingContent(sh.getSpendingContent())
                    .spendingDate(sh.getSpendingDate())
                    .spendingType(sh.getSpendingType())
                    .user(user)
                    .build();
        }else {
            throw new EntityNotFoundException("Spending history not found");
        }
    }

    @Override
    public boolean delete(Long id, Long spendingHistoryId) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );
        Optional<SpendingHistory> optionalSpendingHistory = spendingHistoryRepository.findById(spendingHistoryId);
        if(optionalSpendingHistory.isPresent() && optionalSpendingHistory.get().getUser().getId().equals(id)){
            SpendingHistory sh = optionalSpendingHistory.get();
            spendingHistoryRepository.delete(sh);
            return true;
        }else{
            throw new EntityNotFoundException("Spending history not found");
        }
    }
}
