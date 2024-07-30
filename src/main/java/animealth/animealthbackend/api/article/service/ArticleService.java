package animealth.animealthbackend.api.article.service;

import animealth.animealthbackend.api.article.dto.CreateArticleDTO.CreateArticleRequestDTO;
import animealth.animealthbackend.api.article.dto.CreateArticleDTO.CreateArticleResponseDTO;
import animealth.animealthbackend.api.article.dto.GetArticlePageResponseDTO;
import animealth.animealthbackend.api.article.dto.GetArticleResponseDTO;
import animealth.animealthbackend.api.article.dto.UpdateArticleDTO.UpdateArticleRequestDTO;
import animealth.animealthbackend.api.article.dto.UpdateArticleDTO.UpdateArticleResponseDTO;
import org.springframework.data.domain.Page;

/**
 * ArticleService 인터페이스는 게시글(Article)과 관련된 다양한 기능을 제공합니다.
 * ArticleService interface provides various functionalities related to articles.
 *
 * @author: BullChallenger
 */
public interface ArticleService {

    /**
     * Creates a new article based on the provided request DTO.
     *
     * 주어진 요청 DTO를 기반으로 새로운 게시글을 생성합니다.
     *
     * @param userId the ID of the user who is creating the article
     *               게시글을 작성하는 사용자의 ID
     * @param request the data transfer object containing the information needed to create an article
     *                게시글을 생성하기 위해 필요한 정보가 포함된 데이터 전송 객체
     * @return a response DTO containing the created article's details
     *         생성된 게시글의 세부 정보를 포함하는 응답 DTO
     */
    CreateArticleResponseDTO saveArticle(Long userId, CreateArticleRequestDTO request);

    /**
     * Retrieves an article by its ID.
     *
     * 주어진 ID로 게시글을 조회합니다.
     *
     * @param articleId the ID of the article to retrieve
     *                  조회할 게시글의 ID
     * @return a response DTO containing the retrieved article's details
     *         조회된 게시글의 세부 정보를 포함하는 응답 DTO
     */
    GetArticleResponseDTO getArticleById(Long articleId);

    /**
     * Retrieves articles by page number and criteria.
     *
     * 페이지 번호와 기준에 따라 게시글을 페이징하여 조회합니다.
     *
     * @param pageNo the page number to retrieve
     *               조회할 페이지 번호
     * @param criteria the criteria for sorting and filtering articles
     *                 게시글을 정렬 및 필터링하기 위한 기준
     * @return a page response DTO containing the list of articles for the specified page and criteria
     *         지정된 페이지와 기준에 해당하는 게시글 목록을 포함하는 페이지 응답 DTO
     */
    Page<GetArticlePageResponseDTO> getArticlesByPage(int pageNo, String criteria);

    /**
     * Updates an article based on the provided request DTO.
     *
     * 주어진 요청 DTO를 기반으로 게시글을 업데이트합니다.
     *
     * @param request the data transfer object containing the information needed to update an article
     *                게시글을 업데이트하기 위해 필요한 정보가 포함된 데이터 전송 객체
     * @return a response DTO containing the updated article's details
     *         업데이트된 게시글의 세부 정보를 포함하는 응답 DTO
     */
    UpdateArticleResponseDTO updateArticle(UpdateArticleRequestDTO request);

    /**
     * Deletes an article by its ID.
     *
     * 주어진 ID로 게시글을 삭제합니다.
     *
     * @param articleId the ID of the article to delete
     *                  삭제할 게시글의 ID
     */
    void deleteArticleById(Long articleId);

}