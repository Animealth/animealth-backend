package animealth.animealthbackend.domain.review;

import animealth.animealthbackend.domain.common.BaseEntity;
import animealth.animealthbackend.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@DynamicUpdate
@SQLDelete(sql = "UPDATE review SET IS_DELETED = true WHERE REVIEW_ID=?")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WRITER_ID")
    private User writer;

    @Column(name = "VET_ID")
    private Long vetId;

    @Builder
    public Review(String content, int rating, User writer, Long vetId) {
        this.content = content;
        this.rating = rating;
        this.writer = writer;
        this.vetId = vetId;
    }

    public static final Review of(String content, int rating, User writer, Long vetId) {
        return Review.builder()
                .content(content)
                .rating(rating)
                .writer(writer)
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
