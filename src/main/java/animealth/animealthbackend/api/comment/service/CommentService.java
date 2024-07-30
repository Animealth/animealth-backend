package animealth.animealthbackend.api.comment.service;

import animealth.animealthbackend.api.comment.dto.CreateCommentDTO.CreateCommentRequestDTO;
import animealth.animealthbackend.api.comment.dto.CreateCommentDTO.CreateCommentResponseDTO;
import animealth.animealthbackend.api.comment.dto.GetCommentResponseDTO;
import animealth.animealthbackend.api.comment.dto.UpdateCommentRequestDTO;

/**
 * CommentService 인터페이스는 댓글(Comment)와 관련된 다양한 기능을 제공합니다.
 * CommentService interface provides various functionalities related to comments.
 *
 * @author: BullChallenger
 */
public interface CommentService {

    /**
     * Creates a new comment based on the provided request DTO.
     *
     * 주어진 요청 DTO를 기반으로 새로운 댓글을 생성합니다.
     *
     * @param writerId the ID of the user who is writing the comment
     *                 댓글을 작성하는 사용자의 ID
     * @param request the data transfer object containing the information needed to create a comment
     *                댓글을 생성하기 위해 필요한 정보가 포함된 데이터 전송 객체
     * @return a response DTO containing the created comment's details
     *         생성된 댓글의 세부 정보를 포함하는 응답 DTO
     */
    CreateCommentResponseDTO saveComment(Long writerId, CreateCommentRequestDTO request);

    /**
     * Retrieves a comment by its ID.
     *
     * 주어진 ID로 댓글을 조회합니다.
     *
     * @param commentId the ID of the comment to retrieve
     *                  조회할 댓글의 ID
     * @return a response DTO containing the retrieved comment's details
     *         조회된 댓글의 세부 정보를 포함하는 응답 DTO
     */
    GetCommentResponseDTO findCommentById(Long commentId);

    /**
     * Updates a comment based on the provided request DTO.
     *
     * 주어진 요청 DTO를 기반으로 댓글을 업데이트합니다.
     *
     * @param request the data transfer object containing the information needed to update a comment
     *                댓글을 업데이트하기 위해 필요한 정보가 포함된 데이터 전송 객체
     * @return a response DTO containing the updated comment's details
     *         업데이트된 댓글의 세부 정보를 포함하는 응답 DTO
     */
    GetCommentResponseDTO updateComment(UpdateCommentRequestDTO request);

    /**
     * Deletes a comment by its ID.
     *
     * 주어진 ID로 댓글을 삭제합니다.
     *
     * @param commentId the ID of the comment to delete
     *                  삭제할 댓글의 ID
     */
    void deleteCommentById(Long commentId);
}

