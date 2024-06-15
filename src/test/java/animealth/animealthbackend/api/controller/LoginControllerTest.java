//package animealth.animealthbackend.api.controller;
//
//import animealth.animealthbackend.domain.user.User;
//import animealth.animealthbackend.dummy.DummyUser;
//import animealth.animealthbackend.global.config.auth.dto.SessionUser;
//import jakarta.servlet.http.HttpSession;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.mock.web.MockHttpSession;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.ui.ModelMap;
//
//import static animealth.animealthbackend.domain.user.Role.USER;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(controllers = LoginController.class)
//@ContextConfiguration(classes = {LoginControllerTest.TestConfig.class})
//class LoginControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private HttpSession httpSession;
//
//    @BeforeEach
//    public void setup() {
//        User user1 = DummyUser.createDummyUser("user1", "user1@example.com", "01012341234", "뽀미엄마", USER);
//        SessionUser sessionUser = new SessionUser(user1);
//        when(httpSession.getAttribute("user")).thenReturn(sessionUser);
//    }
//
//    @Test
//    @WithMockUser(username = "user1", roles = {"USER"})
//    public void testIndex_withUserInSession() throws Exception {
//        MvcResult result = mockMvc.perform(get("/"))
//                .andExpect(status().isOk())
////                .andExpect(model().attributeExists("userName"))
////                .andExpect(model().attribute("userName", "user1"))
//                .andExpect(view().name("index"))
//                .andReturn();
//
//        // 테스트 결과를 로그로 출력하여 확인
//        MockHttpServletRequest request = result.getRequest();
//        ModelMap modelMap = result.getModelAndView().getModelMap();
//        Object userName = modelMap.get("userName");
//        System.out.println("나와라: " + userName);
//
//    }
//
//    @Configuration
//    static class TestConfig {
//        @Bean
//        public HttpSession httpSession() {
//            return new MockHttpSession();
//        }
//    }
//}
