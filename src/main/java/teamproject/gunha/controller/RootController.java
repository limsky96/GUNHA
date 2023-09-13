package teamproject.gunha.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class RootController {
  
  @PostMapping("/tmp")
  @ResponseBody
  public Map<String,Object> postTmp(@RequestBody Map<String, Object> json) {

      
    log.info(json.toString());
      
      return json;
  }
  
  @GetMapping("/test")
  public String getLayoutTest(){
    return "layout/layout";
  }

}
