package animealth.animealthbackend.api.article.controller;

import animealth.animealthbackend.api.article.dto.CreateArticleDTO.CreateArticleRequestDTO;
import animealth.animealthbackend.api.article.dto.CreateArticleDTO.CreateArticleResponseDTO;
import animealth.animealthbackend.api.article.dto.GetArticlePageResponseDTO;
import animealth.animealthbackend.api.article.dto.GetArticleResponseDTO;
import animealth.animealthbackend.api.article.dto.UpdateArticleDTO.UpdateArticleRequestDTO;
import animealth.animealthbackend.api.article.dto.UpdateArticleDTO.UpdateArticleResponseDTO;
import animealth.animealthbackend.api.article.service.ArticleService;
import animealth.animealthbackend.api.common.controller.BaseController;
import animealth.animealthbackend.api.common.dto.ResponseDTO;
import animealth.animealthbackend.global.config.auth.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/articles")
public class ArticleController extends BaseController {

    private final ArticleService articleService;

    @PostMapping(value = "/save")
    public ResponseDTO<CreateArticleResponseDTO> saveArticle(HttpSession session, @RequestBody CreateArticleRequestDTO request) {
        SessionUser principal = (SessionUser) session.getAttribute("user");
        return ResponseDTO.ok(articleService.saveArticle(principal.getId(), request));
    }

    @GetMapping(value = "/read")
    public ResponseDTO<Page<GetArticlePageResponseDTO>> getArticles(
            @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
            @RequestParam(required = false, defaultValue = "createdTime", value = "criteria") String criteria
    ) {
        return ResponseDTO.ok(articleService.getArticlesByPage(pageNo, criteria));
    }

    @GetMapping(value = "/read/{articleId}")
    public ResponseDTO<GetArticleResponseDTO> getArticleById(@PathVariable(value = "articleId") Long articleId) {
        return ResponseDTO.ok(articleService.getArticleById(articleId));
    }

    @PostMapping(value = "/update")
    public ResponseDTO<UpdateArticleResponseDTO> updateArticle(@RequestBody UpdateArticleRequestDTO request) {
        return ResponseDTO.ok(articleService.updateArticle(request));
    }

    @PostMapping(value = "/delete/{articleId}")
    public ResponseDTO<Void> deleteArticleById(@PathVariable(value = "articleId") Long articleId) {
        articleService.deleteArticleById(articleId);
        return ResponseDTO.ok();
    }
}

