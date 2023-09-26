package teamproject.gunha.controller;

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.config.WithMockCustomUser;
import teamproject.gunha.security.config.auth.NetflixUserDetails;
import teamproject.gunha.service.UserLoginService;
import teamproject.gunha.vo.ProfileVO;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class ProfileControllerTest {
  
  @Autowired
  private UserLoginService userLoginService;

  @Autowired
  MockMvc mvc;

  @Autowired
  Gson gson;

  @Test
  @WithMockCustomUser(username = "tatelulove4@naver.com_kakao")
  @DisplayName("프로필 관리 페이지")
  void testManageProfile() throws Exception{
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    NetflixUserDetails principal = (NetflixUserDetails)auth.getPrincipal();

    mvc.perform(MockMvcRequestBuilders.get("/profile/manage/"))
          .andExpect(MockMvcResultMatchers.status().isOk());
    
  }
  
  @Test
  @WithMockCustomUser(username = "tatelulove4@naver.com_kakao")
  @DisplayName("프로필 선택하기")
  void testSelectProfile() throws Exception{
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    NetflixUserDetails principal = (NetflixUserDetails)auth.getPrincipal();

    List<ProfileVO> profileList = principal.getUserVO().getProfileList();
    
    String selectedProfile = "테스트";

    Map<String, Object> requestJson = new HashMap<>();

    requestJson.put("userId", principal.getUserVO().getUserId());
    requestJson.put("profileName", selectedProfile);

    Map<String,Object> jsonObject=null;
    Type jsonType = new TypeToken<Map<String, Object>>(){}.getType();
    for(ProfileVO profile : profileList){
      if(selectedProfile.equals(profile.getProfileName())){
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                  .post("/profile/select")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(toJson(requestJson)))
              .andExpect(MockMvcResultMatchers.status().isOk())
              .andReturn();
        MockHttpServletResponse response = result.getResponse();
        jsonObject = fromJson(response.getContentAsString(), jsonType);
      }
    }

    assertNotNull(jsonObject);

    log.info(jsonObject.toString());
  }

  @Test
  @Transactional
  @WithMockCustomUser(username = "tatelulove4@naver.com_kakao") // OAuth2는 직접 Custom을 해줘야함..
  @DisplayName("프로필 생성하기")
  void testCreateProfile() throws Exception{
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    NetflixUserDetails principal = (NetflixUserDetails)auth.getPrincipal();

    List<ProfileVO> profileList = principal.getUserVO().getProfileList();
    
    boolean isExist = false;
    String profileName = "테스트12345";

    Map<String,Object> jsonResponse=null;
    Type jsonType = new TypeToken<Map<String, Object>>(){}.getType();
    for(ProfileVO profile : profileList){
      if(profileName.equals(profile.getProfileName())){
        isExist = true;
      }
    }

    if(!isExist){
      MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post("/profile/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("userId", principal.getUserVO().getUserId())
                .param("profileName", profileName)
              ).andExpect(MockMvcResultMatchers.status().isOk())
              .andReturn();

      MockHttpServletResponse response = result.getResponse();
      jsonResponse = fromJson(response.getContentAsString(), jsonType);
    }

    assertNotNull(jsonResponse);

    log.info(jsonResponse.toString());
  }


  @Test
  @Transactional
  @WithMockCustomUser(username = "tatelulove4@naver.com_kakao")
  @DisplayName("프로필 수정하기")
  void testUpdateProfile() throws Exception{
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    NetflixUserDetails principal = (NetflixUserDetails)auth.getPrincipal();

    List<ProfileVO> profileList = principal.getUserVO().getProfileList();
    
    String originName = "테스트";
    String selectedProfile = "테스트5";

    Map<String,Object> jsonResponse=null;
    Type jsonType = new TypeToken<Map<String, Object>>(){}.getType();
    for(ProfileVO profile : profileList){
      if(originName.equals(profile.getProfileName())){
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                  .patch("/profile/update")
                  .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                  .param("userId", principal.getUserVO().getUserId())
                  .param("originName", originName)
                  .param("profileName", selectedProfile)
              ).andExpect(MockMvcResultMatchers.status().isOk())
              .andReturn();

        MockHttpServletResponse response = result.getResponse();
        jsonResponse = fromJson(response.getContentAsString(), jsonType);

      }
    }

    assertNotNull(jsonResponse);

    log.info(jsonResponse.toString());
  }


  private <T> String toJson(T data) throws JsonProcessingException {
    return gson.toJson(data);
  }

  private <T> T fromJson(String data, Type typeOfT) {
    return gson.fromJson(data, typeOfT);
  }

  
  
}
