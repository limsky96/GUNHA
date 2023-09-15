package teamproject.gunha.admin;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMovieMapper {

    @Select("SELECT * FROM NETFIX_MOVIE")
    List<AdminMovieVO> getAllMovies();

    @Select("SELECT * FROM NETFIX_MOVIE WHERE movieId = #{movieId}")
    AdminMovieVO getMovieById(@Param("movieId") int movieId);

    @Insert("INSERT INTO NETFIX_MOVIE (movieName, moviepostUrl) VALUES (#{movieName}, #{moviepostUrl})")
    @Options(useGeneratedKeys = true, keyProperty = "movieId")
    void saveMovie(AdminMovieVO movie);

    @Delete("DELETE FROM NETFIX_MOVIE WHERE movieId = #{movieId}")
    void deleteMovie(@Param("movieId") int movieId);
}
