package animealth.animealthbackend.api.spending_history.controller;

import static animealth.animealthbackend.domain.user.Role.USER;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import animealth.animealthbackend.api.spending_history.dto.SpendingHistoryDTO;
import animealth.animealthbackend.api.spending_history.service.SpendingHistoryService;
import animealth.animealthbackend.domain.spending_history.SpendingHistoryRepository;
import animealth.animealthbackend.domain.user.User;
import animealth.animealthbackend.global.config.auth.dto.SessionUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

@WebMvcTest(controllers = SpendingHistoryController.class)
@MockBean(JpaMetamodelMappingContext.class) // JPA 관련 빈을 모킹
@MockBean(SpendingHistoryRepository.class) // Repository 빈을 모킹
class SpendingHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SpendingHistoryService spendingHistoryService;

    private SessionUser sessionUser;

    @BeforeEach
    void setUp() {
        sessionUser = new SessionUser(new User(1L, "user1", "user1@example.com", "01012341234", "뽀미엄마", USER, false));
    }

    @DisplayName("가계부 등록 테스트")
    @Test
    void saveSpendingHistory_Test() throws Exception {
        // given
        SpendingHistoryDTO requestDTO = SpendingHistoryDTO.builder()
                .spendingId(1L)
                .spendingContent("Test Content")
                .spendingDate(LocalDate.now())
                .spendingType("Expense")
                .spendingAmount(BigDecimal.valueOf(100))
                .build();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", sessionUser);

        // when
        when(spendingHistoryService.save(anyLong(), any())).thenReturn(requestDTO);

        // then
        mockMvc.perform(post("/api/spending_history/save")
                        .with(oauth2Login())
                        .with(csrf())
                        .session(session)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .contentType(APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.spendingId").value(1L))
                .andExpect(jsonPath("$.spendingContent").value("Test Content"))
                .andExpect(jsonPath("$.spendingDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.spendingType").value("Expense"))
                .andExpect(jsonPath("$.spendingAmount").value(100))
        ;
    }

    @DisplayName("내 가계부 조회 테스트")
    @Test
    void myhistory_Test() throws Exception {
        // given
        SpendingHistoryDTO historyDTO = SpendingHistoryDTO.builder()
                .spendingId(1L)
                .spendingContent("Test Content")
                .spendingDate(LocalDate.now())
                .spendingType("Expense")
                .spendingAmount(BigDecimal.valueOf(100))
                .build();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", sessionUser);

        // when
        when(spendingHistoryService.findById(anyLong())).thenReturn(Collections.singletonList(historyDTO));

        // then
        mockMvc.perform(get("/api/spending_history/myhistory")
                        .with(oauth2Login())
                        .with(csrf())
                        .session(session)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].spendingId").value(1L))
                .andExpect(jsonPath("$[0].spendingContent").value("Test Content"))
                .andExpect(jsonPath("$[0].spendingDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$[0].spendingType").value("Expense"))
                .andExpect(jsonPath("$[0].spendingAmount").value(100))
        ;
    }

    @DisplayName("가계부 수정 테스트")
    @Test
    void updateSpendingHistory_Test() throws Exception {
        // given
        SpendingHistoryDTO requestDTO = SpendingHistoryDTO.builder()
                .spendingId(1L)
                .spendingContent("Updated Content")
                .spendingDate(LocalDate.now())
                .spendingType("Expense")
                .spendingAmount(BigDecimal.valueOf(200))
                .build();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", sessionUser);

        // when
        when(spendingHistoryService.update(anyLong(), any())).thenReturn(requestDTO);

        // then
        mockMvc.perform(put("/api/spending_history/update")
                        .with(oauth2Login())
                        .with(csrf())
                        .session(session)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .contentType(APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.spendingId").value(1L))
                .andExpect(jsonPath("$.spendingContent").value("Updated Content"))
                .andExpect(jsonPath("$.spendingDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.spendingType").value("Expense"))
                .andExpect(jsonPath("$.spendingAmount").value(200))
        ;
    }

    @DisplayName("가계부 삭제 테스트")
    @Test
    void deleteSpendingHistory_Test() throws Exception {
        // given
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", sessionUser);

        // when
        when(spendingHistoryService.delete(anyLong(), anyLong())).thenReturn(true);

        // then
        mockMvc.perform(delete("/api/spending_history/delete/1")
                        .with(oauth2Login())
                        .with(csrf())
                        .session(session)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.result").value(true))
        ;
    }
}
