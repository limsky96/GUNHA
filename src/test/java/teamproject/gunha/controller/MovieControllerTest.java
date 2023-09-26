package teamproject.gunha.controller;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class MovieControllerTest {

  @Autowired
  MockMvc mvc;

  @Autowired
  Gson gson;

  @Test
  @DisplayName("영화 데이터들 가져오기")
  void testGetMovieList() throws Exception {

    MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/movies/api/movies"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

    MockHttpServletResponse response = result.getResponse();
    Type jsonListMapType = new TypeToken<List<Map<String, Object>>>() {}.getType();

    List<Map<String, Object>> respJson = fromJson(response.getContentAsString(), jsonListMapType);

    log.info(respJson.toString());

  }

  @Test
  @DisplayName("영화 데이터 id(primary)값으로 가져오기")
  void testGetMovie() throws Exception {
    long id = 0;
    while (id < 50) {
      id += (long) (Math.random() * 50 + 1);
      if (id >= 50) return;

      MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/movies/api/movies/" + id))
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andReturn();

      MockHttpServletResponse response = result.getResponse();
      Type jsonMapType = new TypeToken<Map<String, Object>>() {}.getType();

      Map<String, Object> respJson = fromJson(response.getContentAsString(), jsonMapType);

      log.info(respJson.toString());

    }
  }


  @Test
  @Transactional
  @DisplayName("영화 데이터 삭제")
  void testDeleteMovie() throws Exception{
    long id = 0;
    while (id < 50) {
      id += (long) (Math.random() * 50 + 1);
      if (id >= 50) return;

      mvc.perform(MockMvcRequestBuilders.delete("/movies/" + id))
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andReturn();

      log.info("지운 영화 id값: " + id);

    }

  }

  private <T> T fromJson(String data, Type typeOfT) {
    return gson.fromJson(data, typeOfT);
  }

}
