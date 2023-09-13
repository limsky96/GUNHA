package teamproject.gunha.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/movies")
public class AdminMovieController {

    @Autowired
    private AdminMovieService adminMovieService;

    @GetMapping("/list")
    public String listMovies(Model model) {
        List<AdminMovieVO> movies = adminMovieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "admin/list"; // 뷰 이름
    }

    @GetMapping("/view/{movieId}")
    public String viewMovie(@PathVariable int movieId, Model model) {
        AdminMovieVO movie = adminMovieService.getMovieById(movieId);
        model.addAttribute("movie", movie);
        return "admin/view";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("movie", new AdminMovieVO());
        return "admin/add";
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
        return "admin/edit";
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
}
