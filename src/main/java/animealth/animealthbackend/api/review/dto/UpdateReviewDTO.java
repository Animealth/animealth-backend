package animealth.animealthbackend.api.review.dto;

import animealth.animealthbackend.domain.review.Review;
import lombok.Builder;
import lombok.Getter;

public class UpdateReviewDTO {

    @Getter
    public static class UpdateReviewRequestDTO {
        private Long reviewId;
        private String content;
        private Integer rating;

        public UpdateReviewRequestDTO(Long reviewId, String content, Integer rating) {
            this.reviewId = reviewId;
            this.content = content;
            this.rating = rating;
        }
    }

    @Getter
    public static class UpdateReviewResponseDTO {

        private final String content;
        private final int rating;

        @Builder
        public UpdateReviewResponseDTO(String content, int rating) {
            this.content = content;
            this.rating = rating;
        }

        public static UpdateReviewResponseDTO fromEntity(Review review) {
            return UpdateReviewResponseDTO.builder()
                    .content(review.getContent())
                    .rating(review.getRating())
                    .build();
        }

    }


}
