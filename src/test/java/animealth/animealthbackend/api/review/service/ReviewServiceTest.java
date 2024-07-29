package animealth.animealthbackend.api.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import animealth.animealthbackend.api.review.dto.CreateReviewDTO.CreateReviewRequestDTO;
import animealth.animealthbackend.api.review.dto.CreateReviewDTO.CreateReviewResponseDTO;
import animealth.animealthbackend.api.review.dto.GetReviewResponseDTO;
import animealth.animealthbackend.api.review.dto.UpdateReviewDTO.UpdateReviewRequestDTO;
import animealth.animealthbackend.api.review.dto.UpdateReviewDTO.UpdateReviewResponseDTO;
import animealth.animealthbackend.domain.user.Role;
import animealth.animealthbackend.domain.user.User;
import animealth.animealthbackend.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ReviewServiceTest {

    @Autowired
    private ReviewServiceImpl reviewService;

    @Autowired private UserRepository userRepository;

    private User writer;
    private Long vetId = 1L;

    @BeforeEach
    void setUp() {
        writer = createWriter("홍길동", "test@test.com", "010-1234-5678", "테스트게정");
        userRepository.save(writer);
    }

    @DisplayName("리뷰 생성 테스트")
    @Test
    void createReview_Test() {
        // given
        CreateReviewRequestDTO request = sendRequest("리뷰 테스트", 4, writer.getId(), vetId);

        // when
        CreateReviewResponseDTO response = reviewService.saveReview(request);

        // then
        assertThat(response).extracting("content", "rating", "writer", "vetId")
                .containsExactlyInAnyOrder("리뷰 테스트", 4, writer.getNickname(), vetId);
    }

    @DisplayName("리뷰 기본키로 리뷰 단일 조회 테스트")
    @Test
    void getReview_By_ReviewId() {
        // given
        CreateReviewRequestDTO saveRequest = sendRequest("리뷰 테스트", 4, writer.getId(), vetId);
        CreateReviewResponseDTO saveResponse = reviewService.saveReview(saveRequest);

        // when
        GetReviewResponseDTO response = reviewService.getReviewByReviewId(saveResponse.getReviewId());

        // then
        assertThat(response)
                .extracting("reviewId", "content", "rating", "writer", "vetId")
                .containsExactlyInAnyOrder(saveResponse.getReviewId(), saveRequest.getContent(), saveRequest.getRating(), writer.getNickname(), vetId);
    }

    @DisplayName("동물병원 기본키로 페이징된 리뷰 조회 테스트")
    @Test
    void getReview_By_VetId_Test() {
        // given
        CreateReviewRequestDTO saveRequest = sendRequest("리뷰 테스트", 4, writer.getId(), vetId);
        CreateReviewResponseDTO saveResponse = reviewService.saveReview(saveRequest);

        // when
        Page<GetReviewResponseDTO> response = reviewService.getReviewByVetId(0, "createdTime", vetId);

        // then
        assertThat(response).hasSize(1);

        assertThat(response.getContent().get(0))
                .extracting("reviewId", "content", "rating", "writer", "vetId")
                .containsExactlyInAnyOrder(saveResponse.getReviewId(), saveRequest.getContent(), saveRequest.getRating(), writer.getNickname(), vetId);
    }

    @DisplayName("작성자 기본키로 페이징된 리뷰 조회 테스트")
    @Test
    void getReview_By_WriterId_Test() {
        // given
        CreateReviewRequestDTO saveRequest = sendRequest("리뷰 테스트", 4, writer.getId(), vetId);
        CreateReviewResponseDTO saveResponse = reviewService.saveReview(saveRequest);

        // when
        Page<GetReviewResponseDTO> response = reviewService.getReviewByWriterId(0, "createdTime", writer.getId());

        // then
        assertThat(response).hasSize(1);

        assertThat(response.getContent().get(0))
                .extracting("reviewId", "content", "rating", "writer", "vetId")
                .containsExactlyInAnyOrder(saveResponse.getReviewId(), saveRequest.getContent(), saveRequest.getRating(), writer.getNickname(), vetId);
    }

    @DisplayName("리뷰 수정 테스트")
    @Test
    void updateReview_Test() {
        // given
        CreateReviewRequestDTO saveRequest = sendRequest("리뷰 테스트", 4, writer.getId(), vetId);
        CreateReviewResponseDTO saveResponse = reviewService.saveReview(saveRequest);

        // when
        UpdateReviewResponseDTO response = reviewService.updateReview(
                new UpdateReviewRequestDTO(saveResponse.getReviewId(), "수정된 리뷰", 3));

        // then
        assertThat(response)
                .extracting("content", "rating")
                .containsExactlyInAnyOrder("수정된 리뷰", 3);
    }

    @DisplayName("리뷰 삭제 테스트")
    @Test
    void deleteReview_By_ReviewId_Test() {
        // given
        CreateReviewRequestDTO saveRequest = sendRequest("리뷰 테스트", 4, writer.getId(), vetId);
        CreateReviewResponseDTO saveResponse = reviewService.saveReview(saveRequest);

        // when
        reviewService.deleteReviewById(saveResponse.getReviewId());

        // then
        assertThatThrownBy(() -> reviewService.getReviewByReviewId(saveResponse.getReviewId()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Review Not Found");
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