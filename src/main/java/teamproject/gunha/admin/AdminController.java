package teamproject.gunha.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.security.config.auth.NetflixUserDetails;
import teamproject.gunha.service.UserLoginService;
import teamproject.gunha.vo.UserVO;

import java.util.List;

@Controller
@Slf4j
public class AdminController {

    @Autowired
    private AdminMovieService adminMovieService;

    @Autowired
    private UserLoginService userLoginService;

    
    @GetMapping("/admin")
     public String admin(@AuthenticationPrincipal NetflixUserDetails netflixUserDetails, Model model) {
        UserVO userVO = netflixUserDetails.getUserVO();
        List<AdminMovieVO> movies = adminMovieService.getAllMovies();
            
        model.addAttribute("user", userVO);
        model.addAttribute("movies", movies);
            
        return "admin/admin";
        }
        
    @GetMapping("/admins-member")
    public String adminMember() {
        log.info("hello()...");
        return "/admins/admin-member-table";
    }

    @GetMapping("/admin-sales")
    public String adminSales() {
        log.info("hello()...");
        return "/admins/admin-sale-table";
    }

    @GetMapping("/admins-movies")
    public String adminMovies() {
        log.info("hello()...");
        return "/admins/admin-movie-table";
    }

    @GetMapping("/admins-addmovies")
    public String adminaddMovies() {
        log.info("hello()...");
        return "/admins/admin-movie-add";
    }

    @GetMapping("/mylist")
    public String mylist() {
        log.info("hello()...");
        return "/homepage/mylist";
    }

}

