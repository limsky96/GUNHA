package teamproject.gunha.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import teamproject.gunha.security.config.auth.NetflixUserDetails;
import teamproject.gunha.service.UserLoginService;
import teamproject.gunha.vo.UserVO;

import java.util.List;

@Controller
@RequestMapping("/admin/movies")
public class AdminMovieController {

    @Autowired
    private AdminMovieService adminMovieService;

     @Autowired
     private UserLoginService userLoginService;

    @GetMapping("/list")
    public String listMovies(Model model) {
        List<AdminMovieVO> movies = adminMovieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "admin/movie/list";
    }

    @GetMapping("/view/{movieId}")
    public String viewMovie(@PathVariable int movieId, Model model) {
        AdminMovieVO movie = adminMovieService.getMovieById(movieId);
        model.addAttribute("movie", movie);
        return "admin/movie/view";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("movie", new AdminMovieVO());
        return "admin/movie/add";
    }

    @PostMapping("/add")
    public String addMovie(@ModelAttribute("movie") AdminMovieVO movie) {
        adminMovieService.saveMovie(movie);
        return "redirect:/admin/movies/list";
    }

    @GetMapping("/edit/{movieId}")
    public String showEditForm(@PathVariable int movieId, Model model) {
        AdminMovieVO movie = adminMovieService.getMovieById(movieId);
        model.addAttribute("movie", movie);
        return "admin/movie/edit";
    }

    @PostMapping("/edit/{movieId}")
    public String editMovie(@PathVariable int movieId, @ModelAttribute("movie") AdminMovieVO movie) {
        adminMovieService.saveMovie(movie);
        return "redirect:/admin/movies/list";
    }

    @GetMapping("/delete/{movieId}")
    public String deleteMovie(@PathVariable int movieId) {
        adminMovieService.deleteMovie(movieId);
        return "redirect:/admin/movies/list";
    }

    // @GetMapping("/admin")
    // public String Admin(Model model) {

    //     List<AdminMovieVO> movies = adminMovieService.getAllMovies();
    //     model.addAttribute("movies", movies);

    //     return "admin/admin";
    // }
    //   @GetMapping("/admin")
    //     public String admin(@AuthenticationPrincipal NetflixUserDetails netflixUserDetails, Model model) {
    //         UserVO userVO = netflixUserDetails.getUserVO();
    //         List<AdminMovieVO> movies = adminMovieService.getAllMovies();
            
    //         model.addAttribute("user", userVO);
    //         model.addAttribute("movies", movies);
            
    //         return "admin/admin";
    //     }
}

