package animealth.animealthbackend.api.article.dto;

import animealth.animealthbackend.api.dto.UserDTO;
import animealth.animealthbackend.domain.article.Article;
import animealth.animealthbackend.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetArticlePageResponseDTO {

    private final Long articleId;
    private final String title;
    private final String writer;

    @Builder
    public GetArticlePageResponseDTO(Long articleId, String title, String writer) {
        this.articleId = articleId;
        this.title = title;
        this.writer = writer;
    }

    public static GetArticlePageResponseDTO of(Article article) {
        return GetArticlePageResponseDTO.builder()
                .articleId(article.getId())
                .title(article.getTitle())
                .writer(article.getWriter().getNickname())
                .build();
    }

}