package animealth.animealthbackend.api.comment.dto;

import animealth.animealthbackend.domain.comment.Comment;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

public class CreateCommentDTO {

    @Getter
    public static class CreateCommentRequestDTO {
        private Long parentCommentId;
        private String content;
        private Long articleId;

        @Builder
        public CreateCommentRequestDTO(Long parentCommentId, String content, Long articleId) {
            this.parentCommentId = parentCommentId;
            this.content = content;
            this.articleId = articleId;
        }
    }

    @Getter
    public static class CreateCommentResponseDTO {
        private final Long commentId;
        private final String writer;
        private final Long articleId;
        private final String content;
        private final int depth;
        private final GetCommentResponseDTO parentComment;
        private final List<Comment> childComments;

        @Builder
        public CreateCommentResponseDTO(Long commentId, String writer, Long articleId, String content, int depth,
                                        Comment parentComment, List<Comment> childComments
        ) {
            this.commentId = commentId;
            this.writer = writer;
            this.articleId = articleId;
            this.content = content;
            this.depth = depth;
            this.parentComment = GetCommentResponseDTO.from(parentComment);
            this.childComments = childComments;
        }

        public static final CreateCommentResponseDTO from(Comment comment) {
            return CreateCommentResponseDTO.builder()
                    .commentId(comment.getId())
                    .writer(comment.getWriter().getNickname())
                    .articleId(comment.getArticleId())
                    .content(comment.getContent())
                    .depth(comment.getDepth())
                    .parentComment(comment.getParentComment())
                    .childComments(comment.getChildComments())
                    .build();
        }
    }

}

