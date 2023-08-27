package teamproject.gunha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.service.UserLoginService;
import teamproject.gunha.vo.UserVO;

@Controller
@Slf4j
public class LoginController {

  @Autowired
  public UserLoginService userLoginService;

  @GetMapping("/login")
  public String loginPage(Model model) {
    try{

    }catch(InternalAuthenticationServiceException e){
      model.addAttribute("error", e.getMessage() );
      return "login-page";
    } finally {
    }
    return "login-page";
  }

  @GetMapping("/sign-up")
  public String signUpPage() {
    return "sign-up";
  }

  @PostMapping("/sign-up")
  public String signUp(UserVO userVO) {
    log.info("signUp() :" + userVO);
    userLoginService.createAccount(userVO);
    return "redirect:/";
  }

}
