package teamproject.gunha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.security.config.auth.NetflixUserDetails;
import teamproject.gunha.service.UserLoginService;
import teamproject.gunha.vo.UserVO;

@Controller
@Slf4j
public class LoginController {

  @Autowired
  public UserLoginService userLoginService;

  @GetMapping("/login")
  public String loginPage(Model model) {

    return "login-page";
  }

  @GetMapping("/sign-up")
  public String signUpPage(
          @AuthenticationPrincipal NetflixUserDetails netflixUserDetails,
          Model model) {
    if(netflixUserDetails != null){
      model.addAttribute("user", netflixUserDetails.getUserVO());
      return "sign-up-social";
    }
    return "sign-up";
  }

  @PostMapping("/sign-up")
  public String signUp(UserVO userVO) {
    log.info("signUp() :" + userVO);
    userLoginService.createAccount(userVO);
    return "redirect:/";
  }

}
