package animealth.animealthbackend.api.comment.dto;

import animealth.animealthbackend.domain.comment.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetCommentResponseDTO {

    private final Long commentId;
    private final String writer;
    private final String content;
    private final int depth;

    @Builder
    public GetCommentResponseDTO(Long commentId, String writer, String content, int depth) {
        this.commentId = commentId;
        this.writer = writer;
        this.content = content;
        this.depth = depth;
    }

    public static GetCommentResponseDTO from(Comment comment) {
        if (comment != null) {
            return GetCommentResponseDTO.builder()
                    .commentId(comment.getId())
                    .writer(comment.getWriter().getNickname())
                    .content(comment.getContent())
                    .depth(comment.getDepth())
                    .build();
        }
        return null;
    }

}

