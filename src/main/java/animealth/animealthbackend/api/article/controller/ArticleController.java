package animealth.animealthbackend.api.article.controller;

import animealth.animealthbackend.api.article.dto.CreateArticleDTO.CreateArticleRequestDTO;
import animealth.animealthbackend.api.article.dto.UpdateArticleDTO.UpdateArticleRequestDTO;
import animealth.animealthbackend.api.article.service.ArticleService;
import animealth.animealthbackend.global.config.auth.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping(value = "/save")
    public String saveArticle(HttpSession session, @RequestBody CreateArticleRequestDTO dto, Model model) {
        SessionUser principal = (SessionUser) session.getAttribute("user");
        model.addAttribute("response", articleService.saveArticle(principal.getId(), dto));
        return "dummyPage";
    }

    @GetMapping(value = "/read")
    public String getArticles(
            @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
            @RequestParam(required = false, defaultValue = "createdTime", value = "criteria") String criteria,
            Model model
    ) {
        model.addAttribute("response", articleService.getArticlesByPage(pageNo, criteria));
        return "dummyPage";
    }

    @GetMapping(value = "/read/{articleId}")
    public String getArticleById(@PathVariable(value = "articleId") Long articleId, Model model) {
        model.addAttribute("response", articleService.getArticleById(articleId));
        return "dummyPage";
    }

    @PostMapping(value = "/update")
    public String updateArticle(@RequestBody UpdateArticleRequestDTO request, Model model) {
        model.addAttribute("response", articleService.updateArticle(request));
        return "dummyPage";
    }

    @PostMapping(value = "/delete/{articleId}")
    public String deleteArticleById(@PathVariable(value = "articleId") Long articleId, Model model) {
        articleService.deleteArticleById(articleId);
        return "dummyPage";
    }

}
