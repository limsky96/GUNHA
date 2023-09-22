package teamproject.gunha.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.security.config.auth.NetflixUserDetails;
import teamproject.gunha.service.MembershipService;
import teamproject.gunha.service.UserLoginService;
import teamproject.gunha.vo.UserVO;


@Controller
@RequestMapping("/my/*")
@Slf4j
public class UserInfoController {
  
  @Autowired
  private UserLoginService userLoginService;

  @Autowired
  private MembershipService membershipService;

  
  @GetMapping("/mylist")
  public String mylist() {
    log.info("hello()...");
    return "/homepage/mylist";
  }

    @GetMapping("/account")
  public String accountpage(Model model) {
    NetflixUserDetails netflixUserDetails = userLoginService.loginAccount();
    UserVO userVO = netflixUserDetails.getUserVO();
    log.info(userVO + "");
    
    model.addAttribute("membership", membershipService.getMembership(userVO.getMembershipNo()));
    model.addAttribute("user", userVO);
    return "homepage/accountpage";
  }

  @GetMapping("/password")
  public String changePasswordPage(@AuthenticationPrincipal NetflixUserDetails netflixUserDetails){
    UserVO userVO = netflixUserDetails.getUserVO();
    if("none".equals(userVO.getSocial())){
      return "login/my/password";
    }
    return "redirect:/my/account";
  }

  @PatchMapping("/password")
  @ResponseBody
  public Map<String,Object> changePassword(@RequestBody Map<String, Object> jsonObject){
    log.info(jsonObject.toString());

    Map<String,Object> response = userLoginService.changeUserPassword(jsonObject);
    return response;
  }


}
