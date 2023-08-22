package teamproject.gunha;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/")
@Log4j2
public class MainController {
    
    @GetMapping("/")
    public String hello(){
       log.info("hello()..."); 
        return "index";
    }
}
