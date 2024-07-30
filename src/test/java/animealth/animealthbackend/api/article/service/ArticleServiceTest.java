package animealth.animealthbackend.api.article.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import animealth.animealthbackend.api.article.dto.CreateArticleDTO.CreateArticleRequestDTO;
import animealth.animealthbackend.api.article.dto.CreateArticleDTO.CreateArticleResponseDTO;
import animealth.animealthbackend.api.article.dto.GetArticleResponseDTO;
import animealth.animealthbackend.api.article.dto.UpdateArticleDTO.UpdateArticleRequestDTO;
import animealth.animealthbackend.domain.article.Article;
import animealth.animealthbackend.domain.comment.Comment;
import animealth.animealthbackend.domain.comment.CommentRepository;
import animealth.animealthbackend.domain.user.Role;
import animealth.animealthbackend.domain.user.User;
import animealth.animealthbackend.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Autowired private UserRepository userRepository;
    @Autowired private CommentRepository commentRepository;

    private User writer;

    @BeforeEach
    void setUp() {
        writer = createWriter("홍길동", "test@test.com", "010-1234-5678", "테스트게정");
        userRepository.save(writer);
    }

    @DisplayName("게시글 생성 테스트")
    @Test
    void createArticle_Test() {
        // given
        CreateArticleRequestDTO request = new CreateArticleRequestDTO("테스트 게시글", "게시글 내용");

        // when
        CreateArticleResponseDTO response = articleService.saveArticle(writer.getId(), request);

        //then
        assertThat(response).extracting("writer", "title", "content")
                .containsExactlyInAnyOrder(writer, request.getTitle(), request.getContent());
    }

    @DisplayName("댓글 없는 게시글 조회 테스트")
    @Test
    void findArticle_By_ArticleId_WithoutComment_Test() {
        // given
        CreateArticleRequestDTO savedRequest = new CreateArticleRequestDTO("테스트 게시글", "게시글 내용");
        CreateArticleResponseDTO savedResponse = articleService.saveArticle(writer.getId(), savedRequest);

        // when
        GetArticleResponseDTO response = articleService.getArticleById(savedResponse.getArticleId());

        // then
        assertThat(response)
                .extracting("articleId", "writer", "title", "content")
                .containsExactlyInAnyOrder(savedResponse.getArticleId(), writer.getNickname(), savedRequest.getTitle(), savedRequest.getContent());

        assertThat(response.getComments()).isEmpty();
    }

    @DisplayName("댓글이 달린 게시글 조회 테스트")
    @Test
    void findArticle_By_ArticleId_WithComment_Test() {
        // given
        CreateArticleRequestDTO savedRequest = new CreateArticleRequestDTO("테스트 게시글", "게시글 내용");
        CreateArticleResponseDTO savedResponse = articleService.saveArticle(writer.getId(), savedRequest);

        List<Comment> commentsOnArticle = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            commentsOnArticle.add(createComment(writer, "댓글입니다 " + i, null, savedResponse.getArticleId()));
        }
        commentRepository.saveAll(commentsOnArticle);

        // when
        GetArticleResponseDTO response = articleService.getArticleById(savedResponse.getArticleId());

        // then
        assertThat(response)
                .extracting("articleId", "writer", "title", "content")
                .containsExactlyInAnyOrder(savedResponse.getArticleId(), writer.getNickname(), savedRequest.getTitle(), savedRequest.getContent());

        assertThat(response.getComments()).hasSize(5);
        assertThat(response.getComments().get(0).getContent()).isEqualTo("댓글입니다 0");
        assertThat(response.getComments().get(response.getComments().size() - 1).getContent()).isEqualTo("댓글입니다 4");
    }

    @DisplayName("게시글 전체 조회 테스트")
    @Test
    void findArticles_Test() {
        // given
        CreateArticleRequestDTO savedRequest = new CreateArticleRequestDTO("테스트 게시글", "게시글 내용");
        articleService.saveArticle(writer.getId(), savedRequest);

        // when, then
        assertThat(articleService.getArticlesByPage(0, "createdTime"))
                .isInstanceOf(Page.class)
                .hasSize(1);
    }

    @DisplayName("게시글 수정 테스트")
    @Test
    void updateArticle_Test() {
        // given
        CreateArticleRequestDTO savedRequest = new CreateArticleRequestDTO("테스트 게시글", "게시글 내용");
        CreateArticleResponseDTO savedResponse = articleService.saveArticle(writer.getId(), savedRequest);

        // when
        UpdateArticleRequestDTO request = new UpdateArticleRequestDTO(savedResponse.getArticleId(), "수정된 게시글 제목", "수정된 게시글 내용");

        // then
        assertThat(articleService.updateArticle(request))
                .extracting("title", "content")
                .containsExactlyInAnyOrder(request.getTitle(), request.getContent());
    }

    @DisplayName("게시글 삭제 테스트")
    @Test
    void deleteArticle_Test() {
        // given
        CreateArticleRequestDTO savedRequest = new CreateArticleRequestDTO("테스트 게시글", "게시글 내용");
        CreateArticleResponseDTO savedResponse = articleService.saveArticle(writer.getId(), savedRequest);

        // when
        articleService.deleteArticleById(savedResponse.getArticleId());

        // then
        assertThatThrownBy(() -> articleService.getArticleById(savedResponse.getArticleId()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Article Not Found");
    }

    // Fixture
    private User createWriter(String name, String email, String phone, String nickname) {
        return User.builder()
                .name(name)
                .email(email)
                .phone(phone)
                .nickname(nickname)
                .role(Role.USER)
                .build();
    }

    // Fixture
    private Comment createComment(User writer, String content, Comment parentComment, Long articleId) {
        return Comment.of(writer, content, parentComment, articleId);
    }

}