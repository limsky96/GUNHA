package teamproject.gunha.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {
  
  @Autowired
  private MockMvc mvc;

  @Test
  @DisplayName("메인 페이지")
  void testMainPage() throws Exception{
    
    this.mvc.perform(MockMvcRequestBuilders.get("/"))
              .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @DisplayName("홈 화면 비로그인 유저 테스트")
  void testAnonymousUser() throws Exception{

    this.mvc.perform(MockMvcRequestBuilders.get("/home"))
          .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

  }

  @Test
  @WithUserDetails("user@example.com")
  @DisplayName("홈 화면 로그인 유저 테스트")
  void testLoginUser() throws Exception{

    this.mvc.perform(MockMvcRequestBuilders.get("/home"))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

}
