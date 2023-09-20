package teamproject.gunha.admin;

import org.apache.ibatis.annotations.*;

import teamproject.gunha.vo.MovieVO;

import java.util.List;

@Mapper
public interface AdminMovieMapper {

    // @Select("SELECT * FROM NETFLIX_MOVIE WHERE movieId = #{movieId}")
    List<AdminMovieVO> getAllMovies();

    // public void insert(MovieVO movie);

    // public void insertSelectKey(MovieVO movie);

    // public MovieVO read(Long movie_id);

    // public int delete(Long movie_id);

    // public int update(MovieVO movie);

    /////////////////////////////////////////////////////////

    AdminMovieVO getMovieById(@Param("movieId") int movieId);

    // @Insert("INSERT INTO NETFLIX_MOVIE (movieName, moviePostUrl) VALUES
    // (#{movieName}, #{moviePostUrl})")
    @Options(useGeneratedKeys = true, keyProperty = "movieId")
    void saveMovie(AdminMovieVO movie);

    // @Delete("DELETE FROM NETFLIX_MOVIE WHERE movieId = #{movieId}")
    void deleteMovie(@Param("movieId") int movieId);
}
