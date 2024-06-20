package animealth.animealthbackend.api.article.controller;

import static animealth.animealthbackend.domain.user.Role.USER;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import animealth.animealthbackend.api.article.dto.CreateArticleDTO.CreateArticleRequestDTO;
import animealth.animealthbackend.api.article.dto.CreateArticleDTO.CreateArticleResponseDTO;
import animealth.animealthbackend.api.article.dto.GetArticlePageResponseDTO;
import animealth.animealthbackend.api.article.dto.GetArticleResponseDTO;
import animealth.animealthbackend.api.article.dto.UpdateArticleDTO.UpdateArticleRequestDTO;
import animealth.animealthbackend.api.article.dto.UpdateArticleDTO.UpdateArticleResponseDTO;
import animealth.animealthbackend.api.article.service.ArticleService;
import animealth.animealthbackend.api.comment.dto.GetCommentResponseDTO;
import animealth.animealthbackend.api.common.constant.ResultType;
import animealth.animealthbackend.domain.user.User;
import animealth.animealthbackend.global.config.auth.dto.SessionUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = ArticleController.class)
class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ArticleService articleService;

    private User writer;

    @BeforeEach
    void setup() {
        writer = new User(1L, "user1", "user1@example.com",
                "01012341234", "뽀미엄마", USER, false);
    }


    @DisplayName("게시글 생성 테스트")
    @Test
    void createArticle_Test() throws Exception {
        // given
        CreateArticleRequestDTO request = new CreateArticleRequestDTO("테스트 게시글", "게시글 내용");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", new SessionUser(writer));

        // when
        when(articleService.saveArticle(anyLong(), any())).thenReturn(CreateArticleResponseDTO.builder()
                .articleId(1L)
                .writer(writer)
                .title(request.getTitle())
                .content(request.getContent())
                .build()
        );

        //then
        mockMvc.perform(post("/api/articles/save")
                        .with(oauth2Login())
                        .with(csrf())
                        .session(session)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.result.code").value(ResultType.SUCCESS.getCode()))
                .andExpect(jsonPath("$.result.desc").value(ResultType.SUCCESS.getDesc()))
                .andExpect(jsonPath("$.data.writer.userId").value(writer.getUserId()))
                .andExpect(jsonPath("$.data.writer.nickname").value(writer.getNickname()))
                .andExpect(jsonPath("$.data.title").value(request.getTitle()))
                .andExpect(jsonPath("$.data.content").value(request.getContent()))
                ;
    }

    @DisplayName("페이징 처리된 게시글 전체 조회")
    @Test
    void findPagingArticles_Test() throws Exception {
        // given
        GetArticlePageResponseDTO article = GetArticlePageResponseDTO.builder()
                .articleId(1L)
                .writer(writer.getNickname())
                .title("테스트 게시글")
                .build();

        List<GetArticlePageResponseDTO> articles = new ArrayList<>() ;
        articles.add(article);

        // when
        int pageSize = 10;
        int totalPages = (int) Math.ceil((double) articles.size() / pageSize);

        Pageable pageable = PageRequest.of(0, pageSize, Sort.by(Sort.Direction.DESC, "createdTime"));
        when(articleService.getArticlesByPage(anyInt(), anyString())).thenReturn(new PageImpl<>(articles, pageable, articles.size()));

        // then
        mockMvc.perform(get("/api/articles/read")
                        .with(oauth2Login())
                        .with(csrf())
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.result.code").value(ResultType.SUCCESS.getCode()))
                .andExpect(jsonPath("$.result.desc").value(ResultType.SUCCESS.getDesc()))
                .andExpect(jsonPath("$.data.totalElements").value(articles.size()))
                .andExpect(jsonPath("$.data.totalPages").value(totalPages))
        ;
    }

    @DisplayName("댓글 없는 단일 게시글 조회")
    @Test
    void findArticle_By_ArticleId_WithoutComment_Test() throws Exception {
        // given, when
        when(articleService.getArticleById(anyLong())).thenReturn(GetArticleResponseDTO.builder()
                .articleId(1L)
                .writer(writer.getNickname())
                .title("테스트 게시글")
                .content("게시글 내용")
                .build()
        );

        // then
        mockMvc.perform(get("/api/articles/read/1")
                        .with(oauth2Login())
                        .with(csrf())
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.result.code").value(ResultType.SUCCESS.getCode()))
                .andExpect(jsonPath("$.result.desc").value(ResultType.SUCCESS.getDesc()))
                .andExpect(jsonPath("$.data.articleId").value(1L))
                .andExpect(jsonPath("$.data.writer").value(writer.getNickname()))
                .andExpect(jsonPath("$.data.title").value("테스트 게시글"))
                .andExpect(jsonPath("$.data.content").value("게시글 내용"))
        ;
    }

    @DisplayName("댓글이 달린 단일 게시글 조회")
    @Test
    void findArticle_By_ArticleId_WithComment_Test() throws Exception {
        // given
        GetArticleResponseDTO response = GetArticleResponseDTO.builder()
                .articleId(1L)
                .writer(writer.getNickname())
                .title("테스트 게시글")
                .content("게시글 내용")
                .build();

        List<GetCommentResponseDTO> commentsOnArticle = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            GetCommentResponseDTO comment = GetCommentResponseDTO.builder()
                    .commentId(Long.valueOf(i))
                    .articleId(1L)
                    .writer(writer.getNickname())
                    .content("댓글 내용 입력")
                    .depth(1)
                    .build();

            commentsOnArticle.add(comment);
        }
        response.setCommentsOnArticle(commentsOnArticle);

        // when
        when(articleService.getArticleById(anyLong())).thenReturn(response);

        // then
        mockMvc.perform(get("/api/articles/read/1")
                        .with(oauth2Login())
                        .with(csrf())
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.result.code").value(ResultType.SUCCESS.getCode()))
                .andExpect(jsonPath("$.result.desc").value(ResultType.SUCCESS.getDesc()))
                .andExpect(jsonPath("$.data.articleId").value(1L))
                .andExpect(jsonPath("$.data.writer").value(writer.getNickname()))
                .andExpect(jsonPath("$.data.title").value("테스트 게시글"))
                .andExpect(jsonPath("$.data.content").value("게시글 내용"))
                .andExpect(jsonPath("$.data.comments", hasSize(10)))
        ;
    }

    @DisplayName("게시글 수정")
    @Test
    void updateArticle_Test() throws Exception {
        // given
        UpdateArticleRequestDTO request = new UpdateArticleRequestDTO(1L, "수정된 게시글", "수정된 게시글 내용입니다.");

        // when
        when(articleService.updateArticle(any())).thenReturn(UpdateArticleResponseDTO.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build()
        );

        // then
        mockMvc.perform(patch("/api/articles/update")
                        .with(oauth2Login())
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.result.code").value(ResultType.SUCCESS.getCode()))
                .andExpect(jsonPath("$.result.desc").value(ResultType.SUCCESS.getDesc()))
                .andExpect(jsonPath("$.data.title").value(request.getTitle()))
                .andExpect(jsonPath("$.data.content").value(request.getContent()))
        ;
    }

    @DisplayName("댓글 삭제")
    @Test
    void deleteArticle_By_ArticleId_Test() throws Exception {
        mockMvc.perform(delete("/api/articles/delete/1")
                        .with(oauth2Login())
                        .with(csrf())
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.result.code").value(ResultType.SUCCESS.getCode()))
                .andExpect(jsonPath("$.result.desc").value(ResultType.SUCCESS.getDesc()))
        ;
    }

}