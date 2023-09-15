package teamproject.gunha.admin;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMovieMapper {


    List<AdminMovieVO> getAllMovies();

    @Select("SELECT * FROM NETFLIX_MOVIE WHERE movieId = #{movieId}")
    AdminMovieVO getMovieById(@Param("movieId") int movieId);

    @Insert("INSERT INTO NETFLIX_MOVIE (movieName, moviePostUrl) VALUES (#{movieName}, #{moviePostUrl})")
    @Options(useGeneratedKeys = true, keyProperty = "movieId")
    void saveMovie(AdminMovieVO movie);

    @Delete("DELETE FROM NETFLIX_MOVIE WHERE movieId = #{movieId}")
    void deleteMovie(@Param("movieId") int movieId);
}
