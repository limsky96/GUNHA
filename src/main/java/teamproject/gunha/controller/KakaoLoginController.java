package teamproject.gunha.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
  public String kakaoCallback(String code, Model model) {
    log.info("kakaoCallback() ...");
    log.info(code);
    OAuthTokenKakao oAuthToken = null;
    oAuthToken = kakaoLoginService.getOAuthToken(code);
    String userId = kakaoLoginService.getKakaoProfile(oAuthToken);
    log.info("userId: "+userId);
    UserVO originUser = userLoginService.getUser(userId);
    // 없는 유저면
    // if (originUser == null) {
    // log.info("없는 유저입니다.");
    // log.info("회원가입 처리 됩니다.");
    // kakaoLoginService.userSignUp(naverUser);
    // }
    NetflixUserDetails netflixUserDetails = new NetflixUserDetails(originUser);
    log.info("originUser: "+originUser);
    log.info("NetflixUserDetails: " + new UsernamePasswordAuthenticationToken(netflixUserDetails, "2577013424", netflixUserDetails.getAuthorities()));

    // 로그인 처리 log.info("로그인 완료");
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(netflixUserDetails, "2577013424", netflixUserDetails.getAuthorities()));
    log.info(authentication.toString());
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String msg = "정상적으로 로그인 되었습니다.";
    String red = "/";
    model.addAttribute("msg", msg);
    model.addAttribute("redirect", red);
    return "redirect:/";
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
