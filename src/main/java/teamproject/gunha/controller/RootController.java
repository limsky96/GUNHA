package teamproject.gunha.controller;

import java.util.Map;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
public class RootController {
  
  @PostMapping("/tmp")
  public Map<String,Object> postTmp(@RequestBody Map<String, Object> json) {

      
    log.info(json.toString());
      
      return json;
  }
  

}
