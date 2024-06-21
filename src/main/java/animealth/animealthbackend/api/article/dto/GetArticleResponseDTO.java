package animealth.animealthbackend.api.article.dto;

import animealth.animealthbackend.api.comment.dto.GetCommentResponseDTO;
import animealth.animealthbackend.domain.article.Article;
import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetArticleResponseDTO implements Serializable {

    private final Long articleId;
    private final String writer;
    private final String title;
    private final String content;
    private List<GetCommentResponseDTO> comments;

    @Builder
    public GetArticleResponseDTO(Long articleId, String writer, String title, String content) {
        this.articleId = articleId;
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public static GetArticleResponseDTO from(Article article) {
        return GetArticleResponseDTO.builder()
                .articleId(article.getId())
                .writer(article.getWriter().getNickname())
                .title(article.getTitle())
                .content(article.getContent())
                .build();
    }

    public void setCommentsOnArticle(List<GetCommentResponseDTO> comments) {
        this.comments = comments;
    }

}
