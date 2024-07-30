package animealth.animealthbackend.domain.comment;

import static org.assertj.core.api.Assertions.*;

import animealth.animealthbackend.domain.article.Article;
import animealth.animealthbackend.domain.article.ArticleRepository;
import animealth.animealthbackend.domain.user.Role;
import animealth.animealthbackend.domain.user.User;
import animealth.animealthbackend.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CommentRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @AfterEach
    void tearDown() {
        commentRepository.deleteAllInBatch();
        articleRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @DisplayName("게시글 고유식별번호를 통해 해당 게시글에 작성된 댓글들 조회")
    @Test
    void findComments_By_ArticleId() {
        User writer = createCommentWriter("홍길동", "test@test.com", "010-1234-5678", "테스트게정");
        userRepository.save(writer);

        Article article = createArticle(writer, "테스트 게시글", "게시글 내용 입력");
        articleRepository.save(article);

        Comment comment = createComment(writer, "댓글 내용 입력", null, article.getId());
        commentRepository.save(comment);

        assertThat(commentRepository.findByArticleId(article.getId())).hasSize(1)
                .extracting("writer", "content", "parentComment", "articleId")
                .containsExactlyInAnyOrder(
                        tuple(writer, "댓글 내용 입력", null, article.getId())
                );
    }

    @DisplayName("존재하지 않는 게시글 고유식별번호를 통해 해당 게시글에 작성된 댓글들 조회")
    @Test
    void findComments_By_Non_Exists_ArticleId() {
        User writer = createCommentWriter("홍길동", "test@test.com", "010-1234-5678", "테스트게정");
        userRepository.save(writer);

        Article article = createArticle(writer, "테스트 게시글", "게시글 내용 입력");
        articleRepository.save(article);

        Comment comment = createComment(writer, "댓글 내용 입력", null, article.getId());
        commentRepository.save(comment);

        assertThat(commentRepository.findByArticleId(999999L)).isEmpty();
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