package animealth.animealthbackend.api.comment.controller;

import static animealth.animealthbackend.domain.user.Role.USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import animealth.animealthbackend.api.comment.dto.CreateCommentDTO.CreateCommentRequestDTO;
import animealth.animealthbackend.api.comment.dto.CreateCommentDTO.CreateCommentResponseDTO;
import animealth.animealthbackend.api.comment.dto.GetCommentResponseDTO;
import animealth.animealthbackend.api.comment.dto.UpdateCommentRequestDTO;
import animealth.animealthbackend.api.comment.service.CommentService;
import animealth.animealthbackend.api.common.constant.ResultType;
import animealth.animealthbackend.domain.user.User;
import animealth.animealthbackend.global.config.auth.dto.SessionUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    private User writer;

    @BeforeEach
    void setup() {
        writer = new User(1L, "user1", "user1@example.com",
                "01012341234", "뽀미엄마", USER, false);
    }

    @DisplayName("댓글 생성")
    @Test
    void createComment_Test() throws Exception {
        // given
        CreateCommentRequestDTO request = CreateCommentRequestDTO.builder()
                .parentCommentId(0L)
                .content("댓글 내용 입력")
                .articleId(1L)
                .build();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", new SessionUser(writer));

        // when
        when(commentService.saveComment(anyLong(), any())).thenReturn(CreateCommentResponseDTO.builder()
                .commentId(1L)
                .writer(writer.getNickname())
                .articleId(1L)
                .content(request.getContent())
                .depth(1)
                .parentComment(null)
                .childComments(null)
                .build()
        );

        // then
        mockMvc.perform(post("/api/comments/save")
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
                .andExpect(jsonPath("$.data.writer").value(writer.getNickname()))
                .andExpect(jsonPath("$.data.content").value(request.getContent()))
        ;
    }

    @DisplayName("단일 댓글 조회")
    @Test
    void findComment_By_CommentId_Test() throws Exception {
        // given, when
        when(commentService.findCommentById(anyLong())).thenReturn(GetCommentResponseDTO.builder()
                .commentId(1L)
                .articleId(1L)
                .writer(writer.getNickname())
                .content("댓글 내용 입력")
                .depth(1)
                .build()
        );

        // then
        mockMvc.perform(get("/api/comments/find/1")
                        .with(oauth2Login())
                        .with(csrf())
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.result.code").value(ResultType.SUCCESS.getCode()))
                .andExpect(jsonPath("$.result.desc").value(ResultType.SUCCESS.getDesc()))
                .andExpect(jsonPath("$.data.commentId").value(1L))
                .andExpect(jsonPath("$.data.writer").value(writer.getNickname()))
                .andExpect(jsonPath("$.data.content").value("댓글 내용 입력"))
        ;
    }

    @DisplayName("댓글 수정")
    @Test
    void updateComment_Test() throws Exception {
        // given
        UpdateCommentRequestDTO request = new UpdateCommentRequestDTO(1L, "수정된 댓글입니다.");

        // when
        when(commentService.updateComment(any())).thenReturn(GetCommentResponseDTO.builder()
                .commentId(1L)
                .articleId(1L)
                .writer(writer.getNickname())
                .content(request.getContent())
                .depth(1)
                .build()
        );

        // then
        mockMvc.perform(patch("/api/comments/update")
                        .with(oauth2Login())
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.result.code").value(ResultType.SUCCESS.getCode()))
                .andExpect(jsonPath("$.result.desc").value(ResultType.SUCCESS.getDesc()))
                .andExpect(jsonPath("$.data.writer").value(writer.getNickname()))
                .andExpect(jsonPath("$.data.content").value(request.getContent()))
        ;
    }

    @DisplayName("댓글 삭제")
    @Test
    void deleteComment_By_CommentId_Test() throws Exception {
        mockMvc.perform(delete("/api/comments/delete/1")
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