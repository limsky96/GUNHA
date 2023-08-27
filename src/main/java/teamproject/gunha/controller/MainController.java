package teamproject.gunha.controller;

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
  public String hello(@AuthenticationPrincipal NetflixUserDetails netflixUserDetails, Model model){
    if(netflixUserDetails != null){
      UserVO userVO = netflixUserDetails.getUserVO();
      if("0000000000000000".equals(userVO.getCardNumber())){
        return "redirect:/sign-up";
      }
      String userEmail = userVO.getUserEmail();
      log.info("user: "+ userEmail);
      model.addAttribute("user", userEmail);
    }
    return "home";
  }

  @GetMapping("/hello")
  @ResponseBody
  public String hello2(){
    JsonObject helloJson = new JsonObject();
    helloJson.addProperty("hello", "hello world");
    return helloJson.toString();
  }


}
