package teamproject.gunha.controller;

import java.util.HashMap;
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

  // 헤더-화이트
  @GetMapping("/header")
  public String header(){

    return "header_white";
  }

  // 해더-블랙
  @GetMapping("/header2")
  public String header2(){

    return "header-black";
  }

  @GetMapping("/")
  public String hello(
      @AuthenticationPrincipal NetflixUserDetails netflixUserDetails,
      Model model) {
    if (netflixUserDetails != null) {
      UserVO userVO = netflixUserDetails.getUserVO();
      String profile = netflixUserDetails.getSelectedProfile();
      if (0 == userVO.getMembershipNo()) {
        return "redirect:/sign-up";
      }

      log.info("userDateils: " + netflixUserDetails);
      model.addAttribute("user", userVO);
      model.addAttribute("selectedProfile", profile);
      return "redirect:/home";
    }
    return "login/index";
  }

  @GetMapping("/hello")
  @ResponseBody
  public ResponseEntity<Object> hello() {
    // JSON 객체 생성
    Map<String, Object> jsonObject = new HashMap<>();
    Map<String, Object> jsonObject2 = new HashMap<>();
    jsonObject.put("key1", "value1");
    jsonObject2.put("key2", "value2");

    jsonObject.put("key3", jsonObject2);
    // ResponseEntity를 사용하여 JSON 객체 반환
    return ResponseEntity.ok(jsonObject);
  }

  @GetMapping("/home")
  public String home(
      @AuthenticationPrincipal NetflixUserDetails netflixUserDetails,
      Model model) {
    if (netflixUserDetails == null) {
      return "redirect:/login";
    }
    UserVO userVO = netflixUserDetails.getUserVO();
    log.info("user: " + userVO);
    model.addAttribute("user", userVO);
    return "home";
  }

  @GetMapping("/watch")
  public String watch(UserVO userVO, Model model) {
    log.info("watch()...");
    model.addAttribute("영상Key", userVO);
    return "watch";
  }

  @GetMapping("/admins")
  public String admin() {
    log.info("hello()...");
    return "admins/admin";
  }

  @GetMapping("/qna")
  public String qna() {
      log.info("qna()...");
      return "qna";
  }




}
