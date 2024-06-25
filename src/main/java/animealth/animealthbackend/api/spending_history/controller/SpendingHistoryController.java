package animealth.animealthbackend.api.spending_history.controller;

import animealth.animealthbackend.api.spending_history.dto.SpendingHistoryDTO;
import animealth.animealthbackend.api.spending_history.service.SpendingHistoryService;
import animealth.animealthbackend.global.config.auth.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/spending_history")
@RequiredArgsConstructor
@Slf4j
public class SpendingHistoryController {
    private final SpendingHistoryService spendingHistoryService;

    //가계부 등록
    @PostMapping(value = "/save")
    public SpendingHistoryDTO save(HttpSession session, @RequestBody SpendingHistoryDTO spendingHistoryDTO) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        return spendingHistoryService.save(sessionUser.getId(), spendingHistoryDTO);
    }

    //내 가계부 조회
    @GetMapping(value = "/myhistory")
    public List<SpendingHistoryDTO> myhistory(HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        return spendingHistoryService.findById(sessionUser.getId());
    }

    //내 가계부 수정
    @PutMapping(value = "/update")
    public SpendingHistoryDTO update(HttpSession session, @RequestBody SpendingHistoryDTO spendingHistoryDTO) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        return spendingHistoryService.update(sessionUser.getId(), spendingHistoryDTO);
    }

    //내 가계부 삭제
    @DeleteMapping(value = "/delete/{spendingHistoryId}")
    public boolean delete(HttpSession session, @PathVariable Long spendingHistoryId) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        return spendingHistoryService.delete(sessionUser.getId(), spendingHistoryId);
    }
}
