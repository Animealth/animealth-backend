package animealth.animealthbackend.api.spending_history.controller;

import animealth.animealthbackend.api.spending_history.dto.SpendingHistoryDTO;
import animealth.animealthbackend.api.spending_history.service.SpendingHistoryServiceImpl;
import animealth.animealthbackend.global.config.auth.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/spending_history")
@RequiredArgsConstructor
@Slf4j
public class SpendingHistoryController {

    private final SpendingHistoryServiceImpl spendingHistoryServiceImpl;

    // 가계부 등록
    @PostMapping(value = "/save")
    public String save(HttpSession session, @ModelAttribute SpendingHistoryDTO spendingHistoryDTO, Model model) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        SpendingHistoryDTO savedHistory = spendingHistoryServiceImpl.save(sessionUser.getId(), spendingHistoryDTO);
        model.addAttribute("spendingHistory", savedHistory);
        return "spending_history/detail"; // 반환할 뷰의 이름
    }

    // 내 가계부 조회
    @GetMapping(value = "/myhistory")
    public String myhistory(HttpSession session, Model model) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        List<SpendingHistoryDTO> historyList = spendingHistoryServiceImpl.findById(sessionUser.getId());
        model.addAttribute("spendingHistoryList", historyList);
        return "spending_history/list"; // 반환할 뷰의 이름
    }

    // 내 가계부 수정
    @PutMapping(value = "/update")
    public String update(HttpSession session, @ModelAttribute SpendingHistoryDTO spendingHistoryDTO, Model model) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        SpendingHistoryDTO updatedHistory = spendingHistoryServiceImpl.update(sessionUser.getId(), spendingHistoryDTO);
        model.addAttribute("spendingHistory", updatedHistory);
        return "spending_history/detail"; // 반환할 뷰의 이름
    }

    // 내 가계부 삭제
    @DeleteMapping(value = "/delete/{spendingHistoryId}")
    public String delete(HttpSession session, @PathVariable Long spendingHistoryId, Model model) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        boolean isDeleted = spendingHistoryServiceImpl.delete(sessionUser.getId(), spendingHistoryId);
        model.addAttribute("isDeleted", isDeleted);
        return "spending_history/delete"; // 반환할 뷰의 이름
    }
}
