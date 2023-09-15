package teamproject.gunha.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMovieMapper {
    List<AdminMovieVO> getAllMovies();

    AdminMovieVO getMovieById(int movieId);

    void saveMovie(AdminMovieVO movie);

    void deleteMovie(int movieId);
}
