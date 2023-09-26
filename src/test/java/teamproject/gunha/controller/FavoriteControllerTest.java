package teamproject.gunha.controller;


import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

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
  @WithMockUser(username = "tatelulove4@naver.com_kakao", roles={"USER"})
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
    Type jsonMapType = new TypeToken<List<Map<String, Object>>>() {}.getType();
    List<Map<String, Object>> respJson = fromJson(response.getContentAsString(), jsonMapType);
    log.info(result.toString());
    log.info(respJson.toString());

  }

  private <T> String toJson(T data) throws JsonProcessingException{
    return gson.toJson(data);
  }

  private <T> T fromJson(String data, Type typeOfT) throws JsonSyntaxException{
    
    return gson.fromJson(data, typeOfT);
  }


}
