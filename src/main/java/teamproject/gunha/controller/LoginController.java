package teamproject.gunha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import teamproject.gunha.service.UserLoginService;
import teamproject.gunha.vo.UserVO;

@Controller
public class LoginController {
  
  @Autowired
  public UserLoginService userLoginService;

  @GetMapping("/login")
  public String loginPage(){
    return "login-page";
  }

  @PostMapping("/sign-up")
  public String signUp(@RequestBody UserVO userVO){
    userLoginService.createAccount(userVO);
    return "redirect:/";
  }

  @GetMapping("/sign-up")
  public String signUpPage(){
    return "sign-up";
  }

}
