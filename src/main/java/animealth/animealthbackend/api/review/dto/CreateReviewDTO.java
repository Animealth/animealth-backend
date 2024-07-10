package animealth.animealthbackend.api.review.dto;

import animealth.animealthbackend.domain.review.Review;
import lombok.Builder;
import lombok.Getter;

public class CreateReviewDTO {

    @Getter
    public static class CreateReviewRequestDTO {

        private String content;
        private int rating;
        private Long writerId;
        private Long vetId;

        @Builder
        public CreateReviewRequestDTO(String content, int rating, Long writerId, Long vetId) {
            this.content = content;
            this.rating = rating;
            this.writerId = writerId;
            this.vetId = vetId;
        }

    }

    public static class CreateReviewResponseDTO {

        private Long reviewId;
        private String content;
        private int rating;
        private String writer;
        private Long vetId;

        @Builder
        public CreateReviewResponseDTO(Long reviewId, String content, int rating, String writer, Long vetId) {
            this.reviewId = reviewId;
            this.content = content;
            this.rating = rating;
            this.writer = writer;
            this.vetId = vetId;
        }

        public static CreateReviewResponseDTO fromEntity(Review review) {
            return CreateReviewResponseDTO.builder()
                    .reviewId(review.getId())
                    .content(review.getContent())
                    .rating(review.getRating())
                    .writer(review.getWriter().getNickname())
                    .vetId(review.getVetId())
                    .build();
        }

    }

}
