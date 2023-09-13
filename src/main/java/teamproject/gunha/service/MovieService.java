package teamproject.gunha.service;

import java.util.List;

import teamproject.gunha.vo.MoviePageVO;
import teamproject.gunha.vo.MovieVO;

public interface MovieService {
    // List<MovieVO> getMovieList();

    List<MovieVO> findMovieList(MoviePageVO moviePageVO);

    boolean isMovieExists(Long id);

    void deleteMovie(Long id);

    void saveMovie(MovieVO movie);

    MovieVO getMovieById(Long id);

    void updateMovie(MovieVO movie);

    void insertMovie(MovieVO movie);

    List<MovieVO> getAllMovies();

}
