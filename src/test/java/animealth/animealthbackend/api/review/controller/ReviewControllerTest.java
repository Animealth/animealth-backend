package animealth.animealthbackend.api.review.controller;

import static animealth.animealthbackend.domain.user.Role.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import animealth.animealthbackend.api.common.constant.ResultType;
import animealth.animealthbackend.api.review.dto.CreateReviewDTO.CreateReviewRequestDTO;
import animealth.animealthbackend.api.review.dto.CreateReviewDTO.CreateReviewResponseDTO;
import animealth.animealthbackend.api.review.dto.GetReviewResponseDTO;
import animealth.animealthbackend.api.review.dto.UpdateReviewDTO.UpdateReviewRequestDTO;
import animealth.animealthbackend.api.review.dto.UpdateReviewDTO.UpdateReviewResponseDTO;
import animealth.animealthbackend.api.review.service.ReviewService;
import animealth.animealthbackend.domain.user.Role;
import animealth.animealthbackend.domain.user.User;
import animealth.animealthbackend.global.config.auth.dto.SessionUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReviewService reviewService;

    private User writer;
    private Long vetId = 1L;

    @BeforeEach
    void setup() {
        writer = new User(1L, "user1", "user1@example.com",
                "01012341234", "뽀미엄마", USER, false);
    }

    @DisplayName("리뷰 생성 테스트")
    @Test
    void saveReview_Test() throws Exception {
        // given
        CreateReviewRequestDTO request = new CreateReviewRequestDTO("리뷰 테스트", 4, writer.getUserId(), vetId);

        // when
        CreateReviewResponseDTO response = CreateReviewResponseDTO.builder()
                .reviewId(1L)
                .content(request.getContent())
                .rating(request.getRating())
                .writer(writer.getNickname())
                .vetId(request.getVetId())
                .build();

        when(reviewService.saveReview(any())).thenReturn(response);

        //then
        mockMvc.perform(post("/api/reviews/save")
                        .with(oauth2Login())
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("dummyPage"))
                .andExpect(model().attributeExists("response"))
                .andExpect(model().attribute("response", response))
        ;
    }

    @DisplayName("리뷰 기본키로 단일 리뷰 조회 테스트")
    @Test
    void getReviews_By_ReviewId_Test() throws Exception {
        // given
        GetReviewResponseDTO response = GetReviewResponseDTO.builder()
                .reviewId(1L)
                .content("리뷰 테스트")
                .rating(4)
                .writer(writer.getNickname())
                .vetId(vetId)
                .build();

        // when
        when(reviewService.getReviewByReviewId(1L)).thenReturn(response);

        // then
        mockMvc.perform(get("/api/reviews/read/1")
                        .with(oauth2Login())
                        .with(csrf())
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("dummyPage"))
                .andExpect(model().attributeExists("response"))
                .andExpect(model().attribute("response", response))
        ;
    }

    @Test
    void getReviews_By_VetId_Test() throws Exception {
        // given
        GetReviewResponseDTO response = GetReviewResponseDTO.builder()
                .reviewId(1L)
                .content("리뷰 테스트")
                .rating(4)
                .writer(writer.getNickname())
                .vetId(vetId)
                .build();

        List<GetReviewResponseDTO> reviews = new ArrayList<>(Arrays.asList(response));

        // when
        int pageSize = 10;

        Pageable pageable = PageRequest.of(0, pageSize, Sort.by(Sort.Direction.DESC, "createdTime"));
        PageImpl<GetReviewResponseDTO> reviewPageInVet = new PageImpl<>(reviews, pageable, reviews.size());
        when(reviewService.getReviewByVetId(anyInt(), anyString(), anyLong())).thenReturn(reviewPageInVet);

        // then
        mockMvc.perform(get("/api/reviews/read/vet/1")
                        .with(oauth2Login())
                        .with(csrf())
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("dummyPage"))
                .andExpect(model().attributeExists("response"))
                .andExpect(model().attribute("response", reviewPageInVet))
        ;
    }

    @Test
    void getReviews_By_WriterId_Test() throws Exception {
        // given
        GetReviewResponseDTO response = GetReviewResponseDTO.builder()
                .reviewId(1L)
                .content("리뷰 테스트")
                .rating(4)
                .writer(writer.getNickname())
                .vetId(vetId)
                .build();

        List<GetReviewResponseDTO> reviews = new ArrayList<>(Arrays.asList(response));

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", new SessionUser(writer));

        // when
        int pageSize = 10;

        Pageable pageable = PageRequest.of(0, pageSize, Sort.by(Sort.Direction.DESC, "createdTime"));
        PageImpl<GetReviewResponseDTO> reviewPageInVet = new PageImpl<>(reviews, pageable, reviews.size());
        when(reviewService.getReviewByWriterId(anyInt(), anyString(), anyLong())).thenReturn(reviewPageInVet);

        // then
        mockMvc.perform(get("/api/reviews/read/my")
                        .with(oauth2Login())
                        .session(session)
                        .with(csrf())
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("dummyPage"))
                .andExpect(model().attributeExists("response"))
                .andExpect(model().attribute("response", reviewPageInVet))
        ;
    }

    @Test
    void updateReview_Test() throws Exception {
        // given
        UpdateReviewRequestDTO request = new UpdateReviewRequestDTO(1L, "수정된 리뷰", 5);

        // when
        UpdateReviewResponseDTO response = UpdateReviewResponseDTO.builder()
                .content(request.getContent())
                .rating(request.getRating())
                .build();

        when(reviewService.updateReview(any())).thenReturn(response);

        // then
        mockMvc.perform(post("/api/reviews/update")
                        .with(oauth2Login())
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("dummyPage"))
                .andExpect(model().attributeExists("response"))
                .andExpect(model().attribute("response", response))
        ;
    }

    @Test
    void deleteReview_By_ReviewId_Test() throws Exception {
        mockMvc.perform(delete("/api/reviews/delete/1")
                        .with(oauth2Login())
                        .with(csrf())
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("dummyPage"))
                .andExpect(model().attributeExists("response"))
                .andExpect(model().attribute("response", "Data successfully deleted"))
        ;
    }

    // Fixture
    private User createWriter(String name, String email, String phone, String nickname) {
        return User.builder()
                .name(name)
                .email(email)
                .phone(phone)
                .nickname(nickname)
                .role(Role.USER)
                .build();
    }

    private CreateReviewRequestDTO sendRequest(String content, int rating, Long writerId, Long vetId) {
        return CreateReviewRequestDTO.builder()
                .content(content)
                .rating(rating)
                .writerId(writerId)
                .vetId(vetId)
                .build();
    }

}