package animealth.animealthbackend.api.review.service;

import animealth.animealthbackend.api.review.dto.CreateReviewDTO.CreateReviewRequestDTO;
import animealth.animealthbackend.api.review.dto.CreateReviewDTO.CreateReviewResponseDTO;
import animealth.animealthbackend.api.review.dto.GetReviewResponseDTO;
import animealth.animealthbackend.api.review.dto.UpdateReviewDTO.UpdateReviewRequestDTO;
import animealth.animealthbackend.api.review.dto.UpdateReviewDTO.UpdateReviewResponseDTO;
import org.springframework.data.domain.Page;

/**
 * ReviewService 인터페이스는 리뷰(Review)와 관련된 다양한 기능을 제공합니다.
 * ReviewService interface provides various functionalities related to reviews.
 *
 * @author: BullChallenger
 */
public interface ReviewService {

    /**
     * Creates a new review based on the provided request DTO.
     *
     * 주어진 요청 DTO를 기반으로 새로운 리뷰를 생성합니다.
     *
     * @param request the data transfer object containing the information needed to create a review
     *                리뷰를 생성하기 위해 필요한 정보가 포함된 데이터 전송 객체
     * @return a response DTO containing the created review's details
     *         생성된 리뷰의 세부 정보를 포함하는 응답 DTO
     */
    CreateReviewResponseDTO saveReview(CreateReviewRequestDTO request);

    /**
     * Retrieves a review by its ID.
     *
     * 주어진 ID로 리뷰를 조회합니다.
     *
     * @param reviewId the ID of the review to retrieve
     *                 조회할 리뷰의 ID
     * @return a response DTO containing the retrieved review's details
     *         조회된 리뷰의 세부 정보를 포함하는 응답 DTO
     */
    GetReviewResponseDTO getReviewByReviewId(Long reviewId);

    /**
     * Retrieves reviews by veterinary ID, paginated by page number and criteria.
     *
     * 동물병원 ID로 리뷰를 조회하며, 페이지 번호와 기준에 따라 페이징합니다.
     *
     * @param pageNo the page number to retrieve
     *               조회할 페이지 번호
     * @param criteria the criteria for sorting and filtering reviews
     *                 리뷰를 정렬 및 필터링하기 위한 기준
     * @param vetId the ID of the veterinary clinic to retrieve reviews for
     *              리뷰를 조회할 동물병원의 ID
     * @return a page response DTO containing the list of reviews for the specified page and criteria
     *         지정된 페이지와 기준에 해당하는 리뷰 목록을 포함하는 페이지 응답 DTO
     */
    Page<GetReviewResponseDTO> getReviewByVetId(int pageNo, String criteria, Long vetId);

    /**
     * Retrieves reviews by writer ID, paginated by page number and criteria.
     *
     * 작성자 ID로 리뷰를 조회하며, 페이지 번호와 기준에 따라 페이징합니다.
     *
     * @param pageNo the page number to retrieve
     *               조회할 페이지 번호
     * @param criteria the criteria for sorting and filtering reviews
     *                 리뷰를 정렬 및 필터링하기 위한 기준
     * @param writerId the ID of the writer to retrieve reviews for
     *                 리뷰를 조회할 작성자의 ID
     * @return a page response DTO containing the list of reviews for the specified page and criteria
     *         지정된 페이지와 기준에 해당하는 리뷰 목록을 포함하는 페이지 응답 DTO
     */
    Page<GetReviewResponseDTO> getReviewByWriterId(int pageNo, String criteria, Long writerId);

    /**
     * Updates a review based on the provided request DTO.
     *
     * 주어진 요청 DTO를 기반으로 리뷰를 업데이트합니다.
     *
     * @param request the data transfer object containing the information needed to update a review
     *                리뷰를 업데이트하기 위해 필요한 정보가 포함된 데이터 전송 객체
     * @return a response DTO containing the updated review's details
     *         업데이트된 리뷰의 세부 정보를 포함하는 응답 DTO
     */
    UpdateReviewResponseDTO updateReview(UpdateReviewRequestDTO request);

    /**
     * Deletes a review by its ID.
     *
     * 주어진 ID로 리뷰를 삭제합니다.
     *
     * @param reviewId the ID of the review to delete
     *                 삭제할 리뷰의 ID
     */
    void deleteReviewById(Long reviewId);
}
