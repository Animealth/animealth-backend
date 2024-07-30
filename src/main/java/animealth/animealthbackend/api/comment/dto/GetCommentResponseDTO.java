package animealth.animealthbackend.api.comment.dto;

import animealth.animealthbackend.domain.comment.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetCommentResponseDTO {

    private final Long commentId;
    private final Long articleId;
    private final String writer;
    private final String content;
    private final int depth;

    @Builder
    public GetCommentResponseDTO(Long commentId, Long articleId, String writer, String content, int depth) {
        this.commentId = commentId;
        this.articleId = articleId;
        this.writer = writer;
        this.content = content;
        this.depth = depth;
    }

    public static GetCommentResponseDTO fromEntity(Comment comment) {
        if (comment != null) {
            return GetCommentResponseDTO.builder()
                    .commentId(comment.getId())
                    .articleId(comment.getArticleId())
                    .writer(comment.getWriter().getNickname())
                    .content(comment.getContent())
                    .depth(comment.getDepth())
                    .build();
        }
        return null;
    }

}

