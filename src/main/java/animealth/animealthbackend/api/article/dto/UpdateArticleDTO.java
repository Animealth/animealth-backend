package animealth.animealthbackend.api.article.dto;

import animealth.animealthbackend.domain.article.Article;
import lombok.Builder;
import lombok.Getter;

public class UpdateArticleDTO {

    @Getter
    public static class UpdateArticleRequestDTO {
        private Long articleId;
        private String title;
        private String content;

        public UpdateArticleRequestDTO(Long articleId, String title, String content) {
            this.articleId = articleId;
            this.title = title;
            this.content = content;
        }
    }

    @Getter
    public static class UpdateArticleResponseDTO {
        private final String title;
        private final String content;

        @Builder
        public UpdateArticleResponseDTO(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public static UpdateArticleResponseDTO from(Article article) {
            return UpdateArticleResponseDTO.builder()
                    .title(article.getTitle())
                    .content(article.getContent())
                    .build();
        }
    }

}

