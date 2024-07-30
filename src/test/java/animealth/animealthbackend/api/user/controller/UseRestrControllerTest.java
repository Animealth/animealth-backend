package animealth.animealthbackend.api.user.controller;

import animealth.animealthbackend.api.user.dto.UserDTO;
import animealth.animealthbackend.api.user.service.CustomOAuth2UserService;
import animealth.animealthbackend.api.user.service.UserServiceImpl;
import animealth.animealthbackend.domain.user.Role;
import animealth.animealthbackend.domain.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest에 비해 웹과 관련된 빈들만 생성해줘서 단위 테스트에 적절하다.
@WebMvcTest(controllers = {UseRestrController.class})

//@WebMvcTest는 테스트에 필요한 모든 빈들을 생성해주지 않기 때문에 필요한 빈들을 목업해줘야 한다.
@MockBeans({
        @MockBean(JpaMetamodelMappingContext.class),
        @MockBean(UserServiceImpl.class),
        @MockBean(CustomOAuth2UserService.class),
        @MockBean(UserRepository.class)
})
class UseRestrControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private UserRepository userRepository;


    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/user/find/all")
                        .with(oauth2Login()))
                        .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "user1", roles = {"USER"})
    void findById() throws Exception {
        mockMvc.perform(get("/user/find/1")
                        .with(oauth2Login()))
                        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user1", roles = {"USER"})
    void update() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        //given
        UserDTO user = UserDTO.builder()
                        .name("user1")
                        .phone("01012341234")
                        .email("example@naver.com")
                        .nickname("산책왕루키")
                        .role(Role.USER)
                        .isDeleted(false)
                        .build();

        when(userServiceImpl.update(anyLong(), any(UserDTO.class))).thenReturn(user);

        //when, then
        mockMvc.perform(put("/user/update/1")
                        .with(csrf())
                        .with(oauth2Login())
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("user1"))
                .andExpect(jsonPath("$.phone").value("01012341234"));
    }

    @Test
    @WithMockUser(username = "user1", roles = {"USER"})
    void delete() throws Exception {
        //given
        UserDTO user = UserDTO.builder()
                .name("user1")
                .phone("01012341234")
                .email("example@naver.com")
                .nickname("산책왕루키")
                .role(Role.USER)
                .isDeleted(true)
                .build();

        when(userServiceImpl.delete(anyLong())).thenReturn(user);

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/1")
                        .with(csrf())
                        .with(oauth2Login())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isDeleted").value(true));
    }
}