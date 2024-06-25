package animealth.animealthbackend.api.spending_history.controller;

import animealth.animealthbackend.api.spending_history.dto.SpendingHistoryDTO;
import animealth.animealthbackend.api.spending_history.service.SpendingHistoryService;
import animealth.animealthbackend.global.config.auth.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        log.info(sessionUser.toString());
        return SpendingHistoryDTO.from(spendingHistoryService.save(sessionUser.getId(), spendingHistoryDTO));
    }
}
