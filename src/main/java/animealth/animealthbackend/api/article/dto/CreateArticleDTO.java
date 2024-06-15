package animealth.animealthbackend.api.article.dto;

import animealth.animealthbackend.domain.article.Article;
import animealth.animealthbackend.domain.user.User;
import lombok.Builder;
import lombok.Getter;

public class CreateArticleDTO {

    @Getter
    public static class CreateArticleRequestDTO {
        private String title;
        private String content;
    }

    @Getter
    public static class CreateArticleResponseDTO {
        private final Long articleId;
        private final User writer;
        private final String title;
        private final String content;

        @Builder
        public CreateArticleResponseDTO(Long articleId, User writer, String title, String content) {
            this.articleId = articleId;
            this.writer = writer;
            this.title = title;
            this.content = content;
        }

        public static CreateArticleResponseDTO from(Article articleEntity) {
            return CreateArticleResponseDTO.builder()
                    .articleId(articleEntity.getId())
                    .writer(articleEntity.getWriter())
                    .title(articleEntity.getTitle())
                    .content(articleEntity.getContent())
                    .build();
        }
    }

}
