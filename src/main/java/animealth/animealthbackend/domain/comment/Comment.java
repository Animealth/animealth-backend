package animealth.animealthbackend.domain.comment;

import static jakarta.persistence.FetchType.LAZY;

import animealth.animealthbackend.domain.common.BaseEntity;
import animealth.animealthbackend.domain.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

@Entity
@Getter
@DynamicUpdate
@Where(clause = "IS_DELETED = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @Column(name = "COMMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "WRITER_ID")
    private User writer;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private int depth;

    @Setter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "PARENT_COMMENT_ID")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = { CascadeType.ALL }, orphanRemoval = true)
    private List<Comment> childComments = new ArrayList<>();

    private Long articleId;

    @Builder
    public Comment(User writer, String content, Comment parentComment, Long articleId) {
        this.writer = writer;
        this.content = content;
        this.parentComment = parentComment;
        this.depth = setDepth(parentComment);
        this.articleId = articleId;
    }

    public static Comment of(final User writer, final String content, final Comment parentComment, final Long articleId) {
        return Comment.builder()
                .writer(writer)
                .content(content)
                .parentComment(parentComment)
                .articleId(articleId)
                .build();
    }

    private int setDepth(Comment parentComment) {
        if (!hasParentComment(parentComment)) {
            return 0;
        }
        return calculateThisCommentDepth(parentComment.getDepth());
    }

    private boolean hasParentComment(Comment parentComment) {
        return parentComment != null;
    }

    private int calculateThisCommentDepth(int parentCommentDepth) {
        return parentCommentDepth + 1;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void addChildComment(Comment childComment) {
        childComment.setParentComment(this);
        this.getChildComments().add(childComment);
    }

}