package teamproject.gunha.controller;

import java.util.Collection;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.security.config.auth.NetflixUserDetails;
import teamproject.gunha.security.vo.OAuthTokenKakao;
import teamproject.gunha.service.KakaoLoginService;
import teamproject.gunha.service.UserLoginService;
import teamproject.gunha.vo.UserVO;

@Slf4j
@Controller
@RequestMapping("/auth/kakao")
public class KakaoLoginController {

  @Autowired
  private UserLoginService userLoginService;
  @Autowired
  private KakaoLoginService kakaoLoginService;
  @Autowired
  private AuthenticationManager authenticationManager;

  @GetMapping("/login")
  public String kakaoLogin() {
    String restApiKey = "59cc93f38a07bc58003afcc13ee2a761";
    String callbackUri = "http://localhost:8888/auth/kakao/callback";
    String requestCallback = "https://kauth.kakao.com/oauth/authorize?client_id=" + restApiKey + "&redirect_uri="
        + callbackUri + "&response_type=code";
    return "redirect:" + requestCallback;
  }

  @GetMapping("/callback")
  @ResponseBody
  public void kakaoCallback(String code, Model model) {
    log.info("kakaoCallback() ...");
    log.info(code);
    OAuthTokenKakao oAuthToken = null;
    oAuthToken = kakaoLoginService.getOAuthToken(code);
    String userId = kakaoLoginService.getKakaoProfile(oAuthToken);
    log.info("userId: " + userId);
    // UserVO originUser = userLoginService.getUser(userId);
    String password = "2577013424";
    // NetflixUserDetails netflixUserDetails = new NetflixUserDetails(originUser);
    // log.info("originUser: "+originUser);
    // log.info("NetflixUserDetails: " + new
    // UsernamePasswordAuthenticationToken(netflixUserDetails, "2577013424",
    // netflixUserDetails.getAuthorities()));

    // // 로그인 처리 log.info("로그인 완료");
    // Authentication authentication = authenticationManager.authenticate(
    // new UsernamePasswordAuthenticationToken(netflixUserDetails, "2577013424",
    // netflixUserDetails.getAuthorities()));
    // log.info(authentication.toString());
    // SecurityContextHolder.getContext().setAuthentication(authentication);

    // String msg = "정상적으로 로그인 되었습니다.";
    // String red = "/";
    // model.addAttribute("msg", msg);
    // model.addAttribute("redirect", red);
    RestTemplate restTemplate = new RestTemplate();
    String targetUrl = "http://localhost:8888/login"; // 대상 URL

    // 폼 데이터 생성
    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("username", userId);
    formData.add("password", password);

    // 요청 헤더 설정
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    // 요청 생성
    HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);

    // POST 요청 보내기
    ResponseEntity<JSONObject> response = restTemplate.exchange(
        targetUrl,
        HttpMethod.POST,
        requestEntity,
        JSONObject.class
    );

    log.info("response: " + response);

  }

  @GetMapping("/logout")
  public String kakaoLogout(Model model) {

    // if (kakaoLoginService.logoutKakao()) {

    // } else {

    // }

    String msg = "정상적으로 로그아웃 되었습니다.";
    String red = "/logout";
    model.addAttribute("msg", msg);
    model.addAttribute("redirect", red);
    return "/alert";
  }
}
