package teamproject.gunha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.security.config.auth.NetflixUserDetails;
import teamproject.gunha.service.UserLoginService;
import teamproject.gunha.vo.UserVO;

@Controller
@Slf4j
@RequestMapping("/profile")
public class ProfileController {
  
  @Autowired
  private UserLoginService userLoginService;

  @GetMapping("/manage")
  public String profileManage(
      @AuthenticationPrincipal NetflixUserDetails netflixUserDetails,
      Model model) {
    if (netflixUserDetails != null) {
      UserVO userVO = netflixUserDetails.getUserVO();
      if (0 == userVO.getMembershipNo()) {
        return "redirect:/sign-up";
      }
      
      log.info("user: " + userVO);
      model.addAttribute("user", userVO);
    }
    return "profile/manage";
  }

}
