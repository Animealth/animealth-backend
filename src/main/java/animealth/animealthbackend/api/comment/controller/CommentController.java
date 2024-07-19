package animealth.animealthbackend.api.comment.controller;

import animealth.animealthbackend.api.comment.dto.CreateCommentDTO.CreateCommentRequestDTO;
import animealth.animealthbackend.api.comment.dto.CreateCommentDTO.CreateCommentResponseDTO;
import animealth.animealthbackend.api.comment.dto.GetCommentResponseDTO;
import animealth.animealthbackend.api.comment.dto.UpdateCommentRequestDTO;
import animealth.animealthbackend.api.comment.service.CommentService;
import animealth.animealthbackend.api.comment.service.CommentServiceImpl;
import animealth.animealthbackend.api.common.controller.BaseController;
import animealth.animealthbackend.api.common.dto.ResponseDTO;
import animealth.animealthbackend.global.config.auth.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value = "/api/comments")
@Controller
public class CommentController extends BaseController {

    private final CommentService commentService;

    @PostMapping(value = "/save")
    public String saveComment(HttpSession session, @RequestBody CreateCommentRequestDTO dto, Model model) {
        SessionUser principal = (SessionUser) session.getAttribute("user");
        model.addAttribute("response", commentService.saveComment(principal.getId(), dto));
        return "dummyPage";
    }

    @GetMapping(value = "/find/{commentId}")
    public String findCommentById(@PathVariable(value = "commentId") Long commentId, Model model) {
        model.addAttribute("response", commentService.findCommentById(commentId));
        return "dummyPage";
    }

    @PostMapping(value = "/update")
    public String updateComment(@RequestBody UpdateCommentRequestDTO request, Model model) {
        model.addAttribute("response", commentService.updateComment(request));
        return "dummyPage";
    }

    @PostMapping(value = "/delete/{commentId}")
    public String deleteCommentById(@PathVariable(value = "commentId") Long commentId, Model model) {
        commentService.deleteCommentById(commentId);
        return "dummyPage";
    }

}
