package animealth.animealthbackend.api.review.dto;

import animealth.animealthbackend.domain.review.Review;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetReviewResponseDTO {

    private Long reviewId;
    private String content;
    private int rating;
    private String writer;
    private Long vetId;

    @Builder
    public GetReviewResponseDTO(Long reviewId, String content, int rating, String writer, Long vetId) {
        this.reviewId = reviewId;
        this.content = content;
        this.rating = rating;
        this.writer = writer;
        this.vetId = vetId;
    }

    public static GetReviewResponseDTO fromEntity(Review review) {
        return GetReviewResponseDTO.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .rating(review.getRating())
                .writer(review.getWriter().getNickname())
                .vetId(review.getVetId())
                .build();
    }

}
