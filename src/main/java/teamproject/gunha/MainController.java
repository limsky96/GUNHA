package teamproject.gunha;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import teamproject.gunha.vo.UserVO;

@Controller
@RequestMapping("/")
@Log4j2
public class MainController {

    @GetMapping("/")
    public String hello() {
        log.info("hello()...");
        return "index";
    }

    @GetMapping("/home")
    public String home() {
        log.info("home()...");
        return "home";
    }


    @GetMapping("/watch")
    public String watch(UserVO userVO, Model model) {
        log.info("watch()...");
        model.addAttribute("영상Key", userVO);
        return "watch";
    }
}
