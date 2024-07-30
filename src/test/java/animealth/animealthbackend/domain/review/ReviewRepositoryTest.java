package animealth.animealthbackend.domain.review;

import static org.assertj.core.api.Assertions.assertThat;

import animealth.animealthbackend.domain.user.Role;
import animealth.animealthbackend.domain.user.User;
import animealth.animealthbackend.domain.user.UserRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    private User writer;
    private User other;

    @BeforeEach
    void setUp() {
        writer = createReviewWriter("홍길동", "test@test.com", "010-1234-5678", "테스트게정");
        other = createReviewWriter("임꺽정", "test02@test.com", "010-4321-5678", "테스트게정02");
        userRepository.saveAll(new ArrayList<>(Arrays.asList(writer, other)));
    }

    @AfterEach
    void tearDown() {
        reviewRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @DisplayName("특정 동물병원에 존재하는 전체 리뷰를 페이징하여 불러오기")
    @Test
    void findAllReviewsByVetId_With_Paging() {
        // given
        Long vetId = 1L;
        List<Review> reviews = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            // 별점은 1점부터 5점까지 반복문의 차수에 따라 지정
            int rating = i % 5 + 1;
            reviews.add(createReview("Test Review " + i, rating, writer, vetId));
        }
        reviewRepository.saveAll(reviews);

        // when
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdTime"));
        Page<Review> reviewPage = reviewRepository.findAllByVetId(pageable, vetId);


        //then
        assertThat(reviewPage).hasSize(10);
        assertThat(reviewPage.getContent().get(0))
                .isInstanceOf(Review.class)
                .extracting("content", "rating", "writer", "vetId")
                .containsExactlyInAnyOrder("Test Review 1", 2, writer, vetId);

        assertThat(reviewPage.getContent().get(reviewPage.getNumberOfElements() - 1))
                .isInstanceOf(Review.class)
                .extracting("content", "rating", "writer", "vetId")
                .containsExactlyInAnyOrder("Test Review 10", 1, writer, vetId);
    }

    @DisplayName("리뷰가 존재하지 않는 동물병원 선택 시 해당 페이지에는 리뷰가 존재하지 않음")
    @Test
    void findAllReviews_Non_Exists_Page_Test() {
        Pageable pageable = PageRequest.of(1, 10, Sort.by(Sort.Direction.DESC, "createdTime"));
        assertThat(reviewRepository.findAllByVetId(pageable, 1L))
                .isInstanceOf(Page.class)
                .hasSize(0);
    }

    @DisplayName("특정 유저가 작성한 리뷰를 페이징하여 불러오기")
    @Test
    void findAllReviewsByWriter_UserId_With_Paging() {
        // given
        Long vetId = 1L;
        List<Review> reviews = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            // 별점은 1점부터 5점까지 반복문의 차수에 따라 지정
            int rating = i % 5 + 1;

            if (i % 2 == 0) {
                reviews.add(createReview("Test Review " + i, rating, writer, vetId));
            } else {
                reviews.add(createReview("Test Review " + i, rating, other, vetId));
            }
        }
        reviewRepository.saveAll(reviews);

        // when
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdTime"));
        Page<Review> reviewPage = reviewRepository.findAllByWriter_Id(pageable, writer.getId());

        //then
        assertThat(reviewPage).hasSize(5);
        assertThat(reviewPage.getContent().get(0))
                .isInstanceOf(Review.class)
                .extracting("content", "rating", "writer", "vetId")
                .containsExactlyInAnyOrder("Test Review 2", 3, writer, vetId);

        assertThat(reviewPage.getContent().get(reviewPage.getNumberOfElements() - 1))
                .isInstanceOf(Review.class)
                .extracting("content", "rating", "writer", "vetId")
                .containsExactlyInAnyOrder("Test Review 10", 1, writer, vetId);
    }

    // Fixture
    private User createReviewWriter(String name, String email, String phone, String nickname) {
        return User.builder()
                .name(name)
                .email(email)
                .phone(phone)
                .nickname(nickname)
                .role(Role.USER)
                .build();
    }

    private Review createReview(String content, int rating, User writer, Long vetId) {
        return Review.of(content, rating, writer, vetId);
    }

}