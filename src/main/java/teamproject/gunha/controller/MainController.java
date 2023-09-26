package teamproject.gunha.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.security.config.auth.NetflixUserDetails;
import teamproject.gunha.service.UserLoginService;
import teamproject.gunha.vo.UserVO;

@Controller
@Slf4j
public class MainController {

  @Autowired
  private UserLoginService userLoginService;

  // // 헤더
  // @GetMapping("/header")
  // public String header() {

  //   return "fragments/header";
  // }

  // // 푸터
  // @GetMapping("/footer")
  // public String footer() {

  //   return "header/footer";
  // }

  // 결제창-카드

  @GetMapping("/")
  public String hello(
      @AuthenticationPrincipal NetflixUserDetails netflixUserDetails,
      Model model) {
    if (netflixUserDetails != null) {
      UserVO userVO = netflixUserDetails.getUserVO();
      String profile = netflixUserDetails.getUserVO().getSelectedProfile();
      if ("결제정보 없음" == userVO.getCardNumber() || 0 == userVO.getMembershipNo()) {
        return "redirect:/regi3";
      }

      log.info("userDateils: " + netflixUserDetails);
      model.addAttribute("user", userVO);
      model.addAttribute("selectedProfile", profile);
      return "redirect:/home";
    }
    return "login/index";
  }

  @GetMapping("/home")
  public String home(
      @AuthenticationPrincipal NetflixUserDetails netflixUserDetails,
      Model model) {
    UserVO userVO = netflixUserDetails.getUserVO();
    log.info("user: " + userVO);
    model.addAttribute("user", userVO);
    return "/homepage/home";
  }

  @GetMapping("/admin-home")
  public String adminHome() {
    return "homepage/admin-home";
  }

  @GetMapping("/watch")
  public String watch() {
    log.info("watch()...");
    return "/watch/watch";
  }

  @GetMapping("/qna")
  public String qna() {
    log.info("qna()...");
    return "homepage/qna";
  }

  @GetMapping("/nodata")
  public String nofunction() {
    log.info("qna()...");
    return "homepage/nofunction";
  }

  @GetMapping("/movie")
  public String movie() {
    log.info("hello()...");
    return "/category/movie";
  }



}
