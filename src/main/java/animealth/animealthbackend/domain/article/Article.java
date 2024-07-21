package animealth.animealthbackend.domain.article;

import animealth.animealthbackend.domain.common.BaseEntity;
import animealth.animealthbackend.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
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
@SQLDelete(sql = "UPDATE article SET IS_DELETED = true WHERE ARTICLE_ID=?")
@Where(clause = "IS_DELETED = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends BaseEntity {

    @Id
    @Column(name = "ARTICLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WRITER_ID")
    private User writer;

    @Column(name = "ARTICLE_TITLE")
    private String title;

    @Lob
    @Column(name = "ARTICLE_CONTENT")
    private String content;

    @Builder
    public Article(User writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public static final Article of(final User writer, final String title, final String content) {
        return Article.builder()
                .writer(writer)
                .title(title)
                .content(content)
                .build();
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

}
