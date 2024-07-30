package animealth.animealthbackend.domain.article;

import static org.assertj.core.api.Assertions.*;

import animealth.animealthbackend.domain.comment.Comment;
import animealth.animealthbackend.domain.comment.CommentRepository;
import animealth.animealthbackend.domain.user.Role;
import animealth.animealthbackend.domain.user.User;
import animealth.animealthbackend.domain.user.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ArticleRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    private User writer;
    private Article article;
    private Comment comment;

    @BeforeEach
    void setUp() {
        writer = createCommentWriter("홍길동", "test@test.com", "010-1234-5678", "테스트게정");
        userRepository.save(writer);

        article = createArticle(writer, "테스트 게시글", "게시글 내용 입력");
        articleRepository.save(article);

        comment = createComment(writer, "댓글 내용 입력", null, article.getId());
        commentRepository.save(comment);
    }

    @AfterEach
    void tearDown() {
        commentRepository.deleteAllInBatch();
        articleRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @DisplayName("게시글 페이징 형태로 가져오기")
    @Test
    void findAllArticles_Paging_Test() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdTime"));
        assertThat(articleRepository.findAll(pageable))
                .isInstanceOf(Page.class)
                .hasSize(1)
                .contains(article);
    }

    @DisplayName("게시글이 존재하지 않는 페이지 선택 시 해당 페이지에는 게시글이 존재하지 않음")
    @Test
    void findAllArticles_Non_Exists_Page_Test() {
        Pageable pageable = PageRequest.of(1, 10, Sort.by(Sort.Direction.DESC, "createdTime"));
        assertThat(articleRepository.findAll(pageable))
                .isInstanceOf(Page.class)
                .hasSize(0)
                .doesNotContain(article);
    }

    @DisplayName("게시글이 생성된 시간 순으로 내림차순 불러오기")
    @Test
    void findAllArticles_Desc_By_CreatedTime_Test() {
        // given
        List<Article> articles = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            articles.add(createArticle(writer, "테스트 게시글 " + i, "게시글 내용 입력"));
        }
        articleRepository.saveAll(articles);

        // when
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdTime"));
        Page<Article> articlePages = articleRepository.findAll(pageable);

        // then
        assertThat(articlePages.getContent().get(0))
                .isInstanceOf(Article.class)
                .extracting("title").hasToString("테스트 게시글");
        assertThat(articlePages.getContent().get(articlePages.getNumberOfElements() - 1))
                .isInstanceOf(Article.class)
                .extracting("title").hasToString("테스트 게시글 9");
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