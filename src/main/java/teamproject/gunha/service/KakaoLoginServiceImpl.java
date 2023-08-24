package teamproject.gunha.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.mapper.UserMapper;
import teamproject.gunha.security.vo.KakaoProfile;
import teamproject.gunha.security.vo.OAuthTokenKakao;
import teamproject.gunha.vo.UserVO;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoLoginServiceImpl implements KakaoLoginService {

  private final UserMapper userMapper;

  @Override
  public OAuthTokenKakao getOAuthToken(String code) {
    RestTemplate rt = new RestTemplate();

    // HttpHeader 오브젝트 생성
    HttpHeaders kakaoAccessTokenRequestHeader = new HttpHeaders();
    kakaoAccessTokenRequestHeader.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    // Http Body 오브젝트 생성
    MultiValueMap<String, String> kakaoAccessTokenRequestBody = new LinkedMultiValueMap<>();
    kakaoAccessTokenRequestBody.add("grant_type", "authorization_code");
    kakaoAccessTokenRequestBody.add("client_id", "59cc93f38a07bc58003afcc13ee2a761");
    kakaoAccessTokenRequestBody.add("redirect_uri", "http://localhost:8888/auth/kakao/callback");
    kakaoAccessTokenRequestBody.add("code", code);
    kakaoAccessTokenRequestBody.add("client_secret ", "S391EswQIsLceXRThnJegdFKxW2vUW3O");

    // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
    HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(kakaoAccessTokenRequestBody,
        kakaoAccessTokenRequestHeader);

    ResponseEntity<String> accessTokenResponse = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,
        accessTokenRequest, String.class);

    // Gson, Json Simple, ObjectMapper
    OAuthTokenKakao oAuthToken = null;
    try {
      oAuthToken = new ObjectMapper().readValue(accessTokenResponse.getBody(), OAuthTokenKakao.class);
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return oAuthToken;
  }

  @Override
  public String getKakaoProfile(OAuthTokenKakao oAuthToken) {
    RestTemplate rt = new RestTemplate();

    // HttpHeader 오브젝트 생성
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
    HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

    ResponseEntity<String> response = rt.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST,
        kakaoProfileRequest, String.class);

    KakaoProfile kakaoProfile = null;
    try {
      kakaoProfile = new ObjectMapper().readValue(response.getBody(), KakaoProfile.class);
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    log.info(kakaoProfile.getId().toString());
		String password = new BCryptPasswordEncoder().encode(kakaoProfile.getId().toString());
    log.info(kakaoProfile.getKakao_account().getEmail().toString());
    log.info(password);
    String userId = kakaoProfile.getKakao_account().getEmail().toString() + "_KAKAO";
    log.info(userId);

    return userId;
  }


  @Override
  public boolean logoutKakao(OAuthTokenKakao oAuthToken) {
    RestTemplate rt = new RestTemplate();

    // HttpHeader 오브젝트 생성
    HttpHeaders kakaoLogoutTokenRequestHeader = new HttpHeaders();
    kakaoLogoutTokenRequestHeader.add("Authorization", "Bearer " + oAuthToken.getAccess_token());

    // Http Body 오브젝트 생성
    HttpEntity<MultiValueMap<String, String>> kakaoLogoutTokenRequest = new HttpEntity<>(null,
        kakaoLogoutTokenRequestHeader);

    rt.exchange("https://kauth.kakao.com/v1/user/logout", HttpMethod.POST,
        kakaoLogoutTokenRequest, String.class);
    return true;
  }

}
