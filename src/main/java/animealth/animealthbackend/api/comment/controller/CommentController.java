package animealth.animealthbackend.api.comment.controller;

import animealth.animealthbackend.api.comment.dto.CreateCommentDTO.CreateCommentRequestDTO;
import animealth.animealthbackend.api.comment.dto.CreateCommentDTO.CreateCommentResponseDTO;
import animealth.animealthbackend.api.comment.dto.GetCommentResponseDTO;
import animealth.animealthbackend.api.comment.dto.UpdateCommentRequestDTO;
import animealth.animealthbackend.api.comment.service.CommentService;
import animealth.animealthbackend.api.common.controller.BaseController;
import animealth.animealthbackend.api.common.dto.ResponseDTO;
import animealth.animealthbackend.global.config.auth.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value = "/api/comments")
@RestController
public class CommentController extends BaseController {

    private final CommentService commentService;

    @PostMapping(value = "/save")
    public ResponseDTO<CreateCommentResponseDTO> saveComment(HttpSession session, @RequestBody CreateCommentRequestDTO dto) {
        SessionUser principal = (SessionUser) session.getAttribute("user");
        return ResponseDTO.ok(commentService.saveComment(principal.getId(), dto));
    }


//    @GetMapping(value = "/find/{commentId}")
//    public String findCommentById(@PathVariable(value = "commentId") Long commentId, Model model) {
//        model.addAttribute("response", commentService.findCommentById(commentId));
//        return "dummyPage";
//    }

    @PostMapping(value = "/update")
    public ResponseDTO<GetCommentResponseDTO> updateComment(@RequestBody UpdateCommentRequestDTO request) {
        return ResponseDTO.ok(commentService.updateComment(request));
    }

    @PostMapping(value = "/delete/{commentId}")
    public ResponseDTO<Void> deleteCommentById(@PathVariable(value = "commentId") Long commentId) {
        commentService.deleteCommentById(commentId);
        return ResponseDTO.ok();
    }

}
