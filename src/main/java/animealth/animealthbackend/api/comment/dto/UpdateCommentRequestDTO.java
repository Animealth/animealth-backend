package animealth.animealthbackend.api.comment.dto;

import lombok.Getter;

@Getter
public class UpdateCommentRequestDTO {

    private Long commentId;
    private String content;

}

