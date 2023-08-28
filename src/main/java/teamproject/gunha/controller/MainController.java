package teamproject.gunha.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.security.config.auth.NetflixUserDetails;
import teamproject.gunha.vo.UserVO;

@Controller
@Slf4j
@RequestMapping("/")
public class MainController {

  @GetMapping("/")
  public String hello(
      @AuthenticationPrincipal NetflixUserDetails netflixUserDetails,
      Model model) {
    if (netflixUserDetails != null) {
      UserVO userVO = netflixUserDetails.getUserVO();
      if ("결제정보 없음".equals(userVO.getCardNumber())) {
        return "redirect:/sign-up";
      }
      String userEmail = userVO.getUserEmail();
      log.info("user: " + userEmail);
      model.addAttribute("user", userEmail);
    }
    return "home";
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

}
