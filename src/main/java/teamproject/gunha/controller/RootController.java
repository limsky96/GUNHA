package teamproject.gunha.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.security.config.auth.NetflixUserDetails;

@Controller
@Slf4j
@RequestMapping("/")
public class RootController {

  @GetMapping("/")
  public String hello(@AuthenticationPrincipal NetflixUserDetails netflixUserDetails, Model model){
    log.info("netflixUserDetails: "+netflixUserDetails);
    if(netflixUserDetails != null){
    String username = netflixUserDetails.getUsername();
    log.info("user: "+ username);
    }
    model.addAttribute("data", "바보");
    return "home";
  }

  @GetMapping("/hello")
  public String hello2(){
    return "{\"hello\":\"hi\"}";
  }


}
