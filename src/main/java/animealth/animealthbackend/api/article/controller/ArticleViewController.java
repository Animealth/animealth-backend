package animealth.animealthbackend.api.article.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/articles")
@Controller
public class ArticleViewController {

    @GetMapping(value = "/create")
    public String createArticle() {
        return "articles/createArticle";
    }

    @GetMapping(value = "/read")
    public String readArticles() {
        return "articles/readArticles";
    }

    @GetMapping(value = "/read/{articleId}")
    public String readArticleById(@PathVariable(value = "articleId") Long articleId, Model model) {
        model.addAttribute("articleId", articleId);
        return "articles/readArticle";
    }

    @GetMapping(value = "/update")
    public String updateArticle() {
        return "articles/updateArticle";
    }

    @GetMapping(value = "/delete")
    public String deleteArticle() {
        return "articles/deleteArticle";
    }

}
