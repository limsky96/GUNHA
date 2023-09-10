package teamproject.gunha.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.security.config.auth.NetflixUserDetails;
import teamproject.gunha.service.UserLoginService;
import teamproject.gunha.vo.UserVO;

@Controller
@Slf4j
public class LoginController {

  @Autowired
  private UserLoginService userLoginService;

  @GetMapping("/login")
  public String login() {
    log.info("hello()...");
    return "login/login-page";
  }

  @GetMapping("/sign-up")
  public String signUpPage(
      @AuthenticationPrincipal NetflixUserDetails netflixUserDetails,
      Model model) {
    if (netflixUserDetails != null) {
      UserVO userVO = netflixUserDetails.getUserVO();
      if ("결제정보 없음".equals(userVO.getCardNumber()) && !"none".equals(userVO.getSocial())) {
        model.addAttribute("user", userVO);
        return "login/sign-up-social";
      }
    }
    return "login/sign-up";
  }

  @PostMapping("/sign-up")
  public String signUp(UserVO userVO) {
    log.info("signUp() :" + userVO);
    userLoginService.createAccount(userVO);
    return "redirect:/";
  }

  @PostMapping("/account-update")
  public String updateAccount(UserVO userVO) {
    log.info("updateAccount() :" + userVO);
    userLoginService.modifyAccount(userVO);
    return "redirect:/";
  }

  @GetMapping("/regi")
  public String regi() {
    log.info("legister()...");
    return "login/regi1";
  }

  @GetMapping("/regi2")
  public String regi2() {
    log.info("legister()...");
    // 계정 있는지 체크 and 로그인
    //근데 체크하려면 ajax로 보내서 확인. -> member-check로 보낸다. 프론트 단에서 할까..?
    // 확인 후 가능하면 계정만들고 로그인해서 다음 페이지로 보내도록 한다
    return "login/regi2";
  }

  @PostMapping("/member-check")
  @ResponseBody
  public ResponseEntity<Map<String, Object>> checkMember(RequestEntity<Map<String,Object>> requestEntity){
    Map<String, Object> responseBody = new HashMap<>();
    Map<String, Object> requestBody = requestEntity.getBody();
    String requestUserId = (String)requestBody.get("user_id");
    if(requestUserId.equals(userLoginService.getUser(requestUserId).getUserId())){
      responseBody.put("msg", "이미 가입된 유저입니다. 로그인하세요");

    } else{
      //로그인 시키는 로직
      //user정보 만들어서 service단으로 넘긴다
      UserVO newUser = UserVO.builder()
          .userId((String)requestBody.get("user_id"))
          .password((String)requestBody.get("password")).build();
      userLoginService.createAccount(newUser);
      // 이후 유저 로그인단
      userLoginService.loginAccount(newUser);
      responseBody.put("redirect_page", "/regi3");
    }
    return new ResponseEntity<Map<String,Object>>(responseBody, HttpStatus.OK);
  }

  @GetMapping("/regi3")
  public String regi3() {
    log.info("legister()...");
    return "login/regi3";
  }

  @GetMapping("/planform")
  public String planform() {
    log.info("planform()...");
    return "login/planform";
  }

  @GetMapping("/member")
  public String updateMember(@AuthenticationPrincipal NetflixUserDetails netflixUserDetails, Model model) {
    UserVO userVO = netflixUserDetails.getUserVO();
    log.info(userVO + "");
    model.addAttribute("user", userVO);
    return "update-member";
  }

}
