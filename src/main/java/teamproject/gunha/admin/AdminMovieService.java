package teamproject.gunha.admin;

import org.springframework.stereotype.Service;

import teamproject.gunha.vo.MovieVO;

import java.util.List;

@Service
public interface AdminMovieService {

    List<AdminMovieVO> getAllMovies();

    // public MovieVO get(Long movie_id);

    // public boolean modify(MovieVO movie);

    // public boolean remove(Long movie_id);

    ////////////////////

    AdminMovieVO getMovieById(int movieId);

    void saveMovie(AdminMovieVO movie);

    void deleteMovie(int movieId);

}
