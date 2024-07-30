package animealth.animealthbackend.api.spending_history.service;

import animealth.animealthbackend.api.spending_history.dto.SpendingHistoryDTO;

import java.util.List;

public interface SpendingHistoryService {

    /**
     * 가계부 등록
     */
    SpendingHistoryDTO save(Long userId, SpendingHistoryDTO spendingHistoryDTO);

    /**
     * 가계부 조회
     */
    List<SpendingHistoryDTO> findById(Long userId);

    /**
     * 가계부 수정
     */
    SpendingHistoryDTO update(Long userId, SpendingHistoryDTO spendingHistoryDTO);

    /**
     * 가계부 삭제
     */
    boolean delete(Long id, Long spendingHistoryId);
}
