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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 상단에 클래스레벨의 @Transactional을 두면 하나하나 붙여주지 않아도 됩니다.
 */
@RequiredArgsConstructor
@Service
public class SpendingHistoryService {
    private final SpendingHistoryRepository spendingHistoryRepository;
    private final UserRepository userRepository;

    //가계부 등록
    @Transactional
    public SpendingHistoryDTO save(Long userId, SpendingHistoryDTO spendingHistoryDTO) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );
        /**
         * 따로 메소드 SpendingHistory로 빼죠 ex) Pet.of()
         */
        SpendingHistory spendingHistory = SpendingHistory.builder()
                .spendingId(spendingHistoryDTO.getSpendingId())
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

    //가계부 읽기

    /**
     * 엔티티 리스트조회
     * 값변환
     * 리턴
     * 나누는게 가독성 있을거 같은데 다들 어캐생각하시는지?
     */
    public List<SpendingHistoryDTO> findById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );
        List<SpendingHistory> spendingHistories = spendingHistoryRepository.findByUserId(userId);
        return spendingHistories.stream()
                .map(SpendingHistoryDTO::from)
                .collect(Collectors.toList());
    }

    //가계부 수정
    @Transactional
    public SpendingHistoryDTO update(Long userId, SpendingHistoryDTO spendingHistoryDTO) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );
        /**
         * SpendingHistory 조회도 User처럼 !
         */
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

    //가계부 삭제

    /**
     * 소프트 딜리트! -> 저도 안한듯 ㅋㅋ
     */
    @Transactional
    public boolean delete(Long id, Long spendingHistoryId) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );
        Optional<SpendingHistory> optionalSpendingHistory = spendingHistoryRepository.findById(spendingHistoryId);
        if(optionalSpendingHistory.isPresent() && optionalSpendingHistory.get().getUser().getUserId().equals(id)){
            SpendingHistory sh = optionalSpendingHistory.get();
            spendingHistoryRepository.delete(sh);
            return true;
        }else{
            throw new EntityNotFoundException("Spending history not found");
        }
    }
}
