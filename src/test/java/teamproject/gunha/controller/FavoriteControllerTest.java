package teamproject.gunha.controller;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.vo.FavoriteVO;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class FavoriteControllerTest {
  

  @Autowired
  private MockMvc mvc;

  @Autowired
  private Gson gson;

  @Test
  @Transactional
  @DisplayName("내가 찜한 컨텐츠 목록 가져오기")
  void testGetFavoriteList() throws Exception{
    
    FavoriteVO fav = FavoriteVO.builder()
                    .userId("tatelulove4@naver.com_kakao")
                    .profileName("테스트")
                    .build();

    MvcResult result = 
            mvc.perform(
              MockMvcRequestBuilders.post("/api/fav")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(fav))
            ).andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();

    MockHttpServletResponse response = result.getResponse();
    
    log.info(response.toString());



  }

  private <T> String toJson(T data) throws JsonProcessingException{
    return gson.toJson(data);
  }



}
