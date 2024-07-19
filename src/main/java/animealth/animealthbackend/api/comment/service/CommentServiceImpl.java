package animealth.animealthbackend.api.comment.service;

import animealth.animealthbackend.api.comment.dto.CreateCommentDTO.CreateCommentRequestDTO;
import animealth.animealthbackend.api.comment.dto.CreateCommentDTO.CreateCommentResponseDTO;
import animealth.animealthbackend.api.comment.dto.GetCommentResponseDTO;
import animealth.animealthbackend.api.comment.dto.UpdateCommentRequestDTO;
import animealth.animealthbackend.domain.comment.Comment;
import animealth.animealthbackend.domain.comment.CommentRepository;
import animealth.animealthbackend.domain.user.User;
import animealth.animealthbackend.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public CreateCommentResponseDTO saveComment(Long writerId, CreateCommentRequestDTO request) {
        Long parentCommentId = request.getParentCommentId();
        User writer = findWriter(writerId);

        if (hasParentComment(parentCommentId)) {
            Optional<Comment> parentComment = commentRepository.findById(parentCommentId);
            if (parentComment.isPresent()) {
                Comment childComment = Comment.of(writer, request.getContent(), parentComment.get(), request.getArticleId());
                parentComment.get().addChildComment(childComment);
                return CreateCommentResponseDTO.fromEntity(commentRepository.save(childComment));
            }
        }

        Comment rootComment = Comment.of(writer, request.getContent(), null, request.getArticleId());
        return CreateCommentResponseDTO.fromEntity(commentRepository.save(rootComment));
    }

    private User findWriter(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User Not Found!")
        );
    }

    private boolean hasParentComment(Long parentCommentId) {
        return parentCommentId != 0;
    }

    @Override
    @Transactional(readOnly = true)
    public GetCommentResponseDTO findCommentById(Long commentId) {
        return GetCommentResponseDTO.fromEntity(commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException("Comment Not Found")
        ));
    }

    @Override
    public GetCommentResponseDTO updateComment(UpdateCommentRequestDTO request) {
        Comment comment = commentRepository.findById(request.getCommentId()).orElseThrow(
                () -> new EntityNotFoundException("Comment Not Found!")
        );

        comment.updateContent(request.getContent());
        return GetCommentResponseDTO.fromEntity(commentRepository.save(comment));
    }

    @Override
    public void deleteCommentById(Long commentId) {
        commentRepository.deleteById(commentId);
    }

}
