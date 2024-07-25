package animealth.animealthbackend.api.comment.service;

import static org.assertj.core.api.Assertions.*;

import animealth.animealthbackend.api.comment.dto.CreateCommentDTO.CreateCommentRequestDTO;
import animealth.animealthbackend.api.comment.dto.CreateCommentDTO.CreateCommentResponseDTO;
import animealth.animealthbackend.api.comment.dto.GetCommentResponseDTO;
import animealth.animealthbackend.api.comment.dto.UpdateCommentRequestDTO;
import animealth.animealthbackend.domain.article.Article;
import animealth.animealthbackend.domain.article.ArticleRepository;
import animealth.animealthbackend.domain.comment.Comment;
import animealth.animealthbackend.domain.user.Role;
import animealth.animealthbackend.domain.user.User;
import animealth.animealthbackend.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CommentServiceTest {

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired private UserRepository userRepository;
    @Autowired private ArticleRepository articleRepository;

    private User writer;
    private Article article;

    @BeforeEach
    void setUp() {
        writer = createCommentWriter("홍길동", "test@test.com", "010-1234-5678", "테스트게정");
        userRepository.save(writer);

        article = createArticle(writer, "테스트 게시글", "게시글 내용 입력");
        articleRepository.save(article);
    }

    @DisplayName("댓글 생성 테스트")
    @Test
    void saveComment_test() {
        // given
        CreateCommentRequestDTO request = CreateCommentRequestDTO.builder()
                .parentCommentId(0L)
                .content("댓글 내용 입력")
                .articleId(article.getId())
                .build();

        // when
        CreateCommentResponseDTO response = commentService.saveComment(writer.getUserId(), request);

        // then
        assertThat(response).extracting("writer", "articleId", "content", "depth", "parentComment")
                .containsExactlyInAnyOrder(writer.getNickname(), article.getId(), "댓글 내용 입력", 0, null);
        assertThat(response.getChildComments()).isEmpty();
    }

    @DisplayName("대댓글 생성 테스트")
    @Test
    void saveChildComment_test() {
        // given
        CreateCommentRequestDTO parentRequest = CreateCommentRequestDTO.builder()
                .parentCommentId(0L)
                .content("댓글 내용 입력")
                .articleId(article.getId())
                .build();

        CreateCommentResponseDTO parentResponse = commentService.saveComment(writer.getUserId(), parentRequest);

        // when
        CreateCommentRequestDTO childRequest = CreateCommentRequestDTO.builder()
                .parentCommentId(parentResponse.getCommentId())
                .content("대댓글입니다.")
                .articleId(article.getId())
                .build();

        CreateCommentResponseDTO childResponse = commentService.saveComment(writer.getUserId(), childRequest);
        GetCommentResponseDTO getParentResponse = commentService.findCommentById(parentResponse.getCommentId());

        // then
        assertThat(childResponse).extracting("writer", "articleId", "content", "depth")
                .containsExactlyInAnyOrder(writer.getNickname(), article.getId(), "대댓글입니다.", 1);
        assertThat(childResponse.getChildComments()).isEmpty();
        assertThat(childResponse.getParentComment()).usingRecursiveComparison().isEqualTo(getParentResponse);

        assertThat(parentResponse.getChildComments()).hasSize(1);
    }

    @DisplayName("댓글 조회 테스트")
    @Test
    void findComment_By_CommentId_test() {
        // given
        CreateCommentRequestDTO saveRequest = CreateCommentRequestDTO.builder()
                .parentCommentId(0L)
                .content("댓글 내용 입력")
                .articleId(article.getId())
                .build();

        CreateCommentResponseDTO saveResponse = commentService.saveComment(writer.getUserId(), saveRequest);

        // when
        GetCommentResponseDTO response = commentService.findCommentById(saveResponse.getCommentId());

        //then
        assertThat(response).extracting("writer", "articleId", "content", "depth")
                .containsExactlyInAnyOrder(writer.getNickname(), article.getId(), "댓글 내용 입력", 0);
    }

    @DisplayName("댓글 수정 테스트")
    @Test
    void updateComment_test() {
        // given
        CreateCommentRequestDTO saveRequest = CreateCommentRequestDTO.builder()
                .parentCommentId(0L)
                .content("댓글 내용 입력")
                .articleId(article.getId())
                .build();

        CreateCommentResponseDTO saveResponse = commentService.saveComment(writer.getUserId(), saveRequest);

        // when
        UpdateCommentRequestDTO request = new UpdateCommentRequestDTO(saveResponse.getCommentId(), "수정된 댓글 내용");
        GetCommentResponseDTO response = commentService.updateComment(request);

        //then
        assertThat(response).extracting("writer", "content", "depth")
                .containsExactlyInAnyOrder(writer.getNickname(), "수정된 댓글 내용", 0);
    }

    @DisplayName("댓글 삭제 테스트")
    @Test
    void deleteComment_By_CommentId_test() {
        // given
        CreateCommentRequestDTO saveRequest = CreateCommentRequestDTO.builder()
                .parentCommentId(0L)
                .content("댓글 내용 입력")
                .articleId(article.getId())
                .build();

        CreateCommentResponseDTO saveResponse = commentService.saveComment(writer.getUserId(), saveRequest);

        // when
        commentService.deleteCommentById(saveResponse.getCommentId());

        //then
        assertThatThrownBy(() -> commentService.findCommentById(saveResponse.getCommentId()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Comment Not Found");
    }

    // Fixture
    private User createCommentWriter(String name, String email, String phone, String nickname) {
        return User.builder()
                .name(name)
                .email(email)
                .phone(phone)
                .nickname(nickname)
                .role(Role.USER)
                .build();
    }

    // Fixture
    private Article createArticle(User writer, String title, String content) {
        return Article.of(writer, title, content);
    }

    // Fixture
    private Comment createComment(User writer, String content, Comment parentComment, Long articleId) {
        return Comment.of(writer, content, parentComment, articleId);
    }

}