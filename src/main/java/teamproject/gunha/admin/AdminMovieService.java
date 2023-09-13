package teamproject.gunha.admin;

import java.util.List;

public interface AdminMovieService {
    List<AdminMovieVO> getAllMovies();

    AdminMovieVO getMovieById(int movieId);

    void saveMovie(AdminMovieVO movie);

    void deleteMovie(int movieId);
}
