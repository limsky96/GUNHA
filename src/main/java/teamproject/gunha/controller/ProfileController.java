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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
      log.info("user: " + userVO);
      model.addAttribute("user", userVO);
    }
    return "profile/manage";
  }

  @PostMapping("/select")
  @ResponseBody
  public Map<String,Object> selectProfile(@AuthenticationPrincipal NetflixUserDetails netflixUserDetails, @RequestBody Map<String, Object> jsonObject){
    log.info("userInfo: " + netflixUserDetails);
    log.info("requestbody: " +jsonObject);
    userLoginService.loginAccount(jsonObject);
    return jsonObject;
  }

  @PostMapping("/create")
  @ResponseBody
  public Map<String, Object> createProfile(ProfileVO profileVO){
    log.info("createProfile() :...");
    log.info(profileVO + "");
    
    Map<String, Object> json = new HashMap<>();
    if(profileService.createProfile(profileVO)){
      json.put("msg", "create done");
      json.put("redirectPage", "/profile/manage");
      json.put("returnedValue", true);
    }else{
      json.put("msg", "create failed");
      json.put("returnedValue", false);
    }
    return json;
  } 


  
  @PatchMapping("/update")
  @ResponseBody
  public Map<String, Object> updateProfile(
    ProfileVO profileVO){
    log.info(profileVO + "");
    Map<String, Object> json = new HashMap<>();
    if(profileService.updateProfile(profileVO)){
      json.put("msg", "update done");
      json.put("redirectPage", "/profile/manage");
      json.put("returnedValue", true);
    }else{
      json.put("msg", "update failed");
      json.put("returnedValue", false);
    }
    return json;
  } 



  @DeleteMapping("/delete")
  @ResponseBody
  public Map<String, Object> deleteProfile(ProfileVO profileVO){
    log.info(profileVO + "");
    Map<String, Object> json = new HashMap<>();
    if(profileService.removeProfile(profileVO)){
      json.put("msg", "delete done");
      json.put("redirectPage", "/profile/manage");
      json.put("returnedValue", true);
    }else{
      json.put("msg", "delete failed");
      json.put("returnedValue", false);
    }
    return json; 
  }

  @PostMapping("/mylist")
  @ResponseBody
  public Map<String, Object> addFavorite(@RequestBody Map<String, Object> jsonObject){

    Map<String, Object> json = new HashMap<>();

    // if(profileService.addFavorite(jsonObject)){

    // }


    return jsonObject;

  }


}
