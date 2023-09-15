package teamproject.gunha.admin;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminMovieService {

    List<AdminMovieVO> getAllMovies();

    AdminMovieVO getMovieById(int movieId);

    void saveMovie(AdminMovieVO movie);

    void deleteMovie(int movieId);
}
