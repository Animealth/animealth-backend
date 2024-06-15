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
    }

    @Getter
    public static class CreateCommentResponseDTO {
        private final Long commentId;
        private final String writer;
        private final String content;
        private final int depth;
        private final GetCommentResponseDTO parentComment;
        private final List<Comment> childComments;

        @Builder
        public CreateCommentResponseDTO(Long commentId, String writer, String content, int depth, Comment parentComment, List<Comment> childComments) {
            this.commentId = commentId;
            this.writer = writer;
            this.content = content;
            this.depth = depth;
            this.parentComment = GetCommentResponseDTO.from(parentComment);
            this.childComments = childComments;
        }

        public static final CreateCommentResponseDTO from(Comment comment) {
            return CreateCommentResponseDTO.builder()
                    .commentId(comment.getId())
                    .writer(comment.getWriter().getNickname())
                    .content(comment.getContent())
                    .depth(comment.getDepth())
                    .parentComment(comment.getParentComment())
                    .childComments(comment.getChildComments())
                    .build();
        }
    }

}

