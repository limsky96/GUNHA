package teamproject.gunha.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.security.config.auth.NetflixUserDetails;
import teamproject.gunha.service.ProfileService;
import teamproject.gunha.service.UserLoginService;
import teamproject.gunha.vo.ProfileVO;
import teamproject.gunha.vo.UserVO;

@Controller
@Slf4j
@RequestMapping("/profile")
public class ProfileController {
  
  @Autowired
  private UserLoginService userLoginService;

  @Autowired
  private ProfileService profileService;


  @GetMapping("/manage")
  public String manageProfile(
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

  @PatchMapping("/update")
  @ResponseBody
  public Map<String, Object> updateProfile(ProfileVO profileVO){
    log.info(profileVO + "");
    Map<String, Object> json = new HashMap<>();
    if(true){
      json.put("msg", "delete done");
    }
    return json;
  } 



  @DeleteMapping("/delete")
  @ResponseBody
  public Map<String, Object> deleteProfile(ProfileVO profileVO){
    log.info(profileVO + "");
    Map<String, Object> json = new HashMap<>();
    if(true){
      json.put("msg", "delete done");
    }
    return json; 
  }


}
