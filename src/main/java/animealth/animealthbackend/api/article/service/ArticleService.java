package animealth.animealthbackend.api.article.service;

import animealth.animealthbackend.api.article.dto.CreateArticleDTO.*;
import animealth.animealthbackend.api.article.dto.GetArticlePageResponseDTO;
import animealth.animealthbackend.api.article.dto.GetArticleResponseDTO;
import animealth.animealthbackend.api.article.dto.UpdateArticleDTO.UpdateArticleRequestDTO;
import animealth.animealthbackend.api.article.dto.UpdateArticleDTO.UpdateArticleResponseDTO;
import animealth.animealthbackend.domain.article.Article;
import animealth.animealthbackend.domain.article.ArticleRepository;
import animealth.animealthbackend.domain.user.User;
import animealth.animealthbackend.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleService {

    private static final int PAGE_SIZE = 10;

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Transactional
    public Article saveArticle(Long userId, CreateArticleRequestDTO request) {
        User writer = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User Not Found!")
        );

        return articleRepository.save(
            Article.of(
                writer,
                request.getTitle(),
                request.getContent()
            )
        );
    }

    @Transactional(readOnly = true)
    public GetArticleResponseDTO getArticleById(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new EntityNotFoundException("Article Not Found")
        );
        return GetArticleResponseDTO.from(article);
    }

    @Transactional(readOnly = true)
    public Page<GetArticlePageResponseDTO> getArticlesByPage(int pageNo, String criteria) {
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, Sort.by(Sort.Direction.DESC, criteria));
        return articleRepository.findAll(pageable).map(GetArticlePageResponseDTO::of);
    }

    @Transactional
    public UpdateArticleResponseDTO updateArticle(UpdateArticleRequestDTO request) {
        Article article = articleRepository.findById(request.getArticleId()).orElseThrow(
                () -> new EntityNotFoundException("Article Not Found!")
        );

        if (request.getTitle() != null) {
            article.updateTitle(request.getTitle());
        }
        if (request.getContent() != null) {
            article.updateContent(request.getContent());
        }

        return UpdateArticleResponseDTO.from(article);
    }

    @Transactional
    public void deleteArticleById(Long articleId) {
        articleRepository.deleteById(articleId);
    }

}
