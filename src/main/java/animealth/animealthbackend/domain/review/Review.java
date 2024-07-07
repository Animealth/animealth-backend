package animealth.animealthbackend.domain.review;

import animealth.animealthbackend.domain.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

@Entity
@Getter
@DynamicUpdate
@Where(clause = "IS_DELETED = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @Column(name = "REVIEW_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "REVIEW_CONTENT")
    private String content;

    @Column(name = "RATING")
    private int rating;

    @Column(name = "WRITER_ID")
    private Long writerId;

    @Column(name = "VET_ID")
    private Long vetId;

    @Builder
    public Review(String content, int rating, Long writerId, Long vetId) {
        this.content = content;
        this.rating = rating;
        this.writerId = writerId;
        this.vetId = vetId;
    }

    public static final Review of(String content, int rating, Long writerId, Long vetId) {
        return Review.builder()
                .content(content)
                .rating(rating)
                .writerId(writerId)
                .vetId(vetId)
                .build();
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateRating(int rating) {
        this.rating = rating;
    }

}
