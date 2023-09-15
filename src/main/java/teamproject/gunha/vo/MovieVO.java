package teamproject.gunha.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieVO {
    private Long movieId;
    private String movieName;
    private String moviepostUrl;
    private String movietrailerUrl;
    private String movieContent;
    private String movieGenre;
    private String movieCast;
    private Date movieReleaseDate;
    private boolean movieFavorite;
    private boolean autoplay;

    public void updateFrom(MovieVO updatedMovie) {
        if (updatedMovie.getMovieName() != null) {
            this.movieName = updatedMovie.getMovieName();
        }
        if (updatedMovie.getMoviepostUrl() != null) {
            this.moviepostUrl = updatedMovie.getMoviepostUrl();
        }
        if (updatedMovie.getMovietrailerUrl() != null) {
            this.movietrailerUrl = updatedMovie.getMovietrailerUrl();
        }
        if (updatedMovie.getMovieContent() != null) {
            this.movieContent = updatedMovie.getMovieContent();
        }
        if (updatedMovie.getMovieGenre() != null) {
            this.movieGenre = updatedMovie.getMovieGenre();
        }
        if (updatedMovie.getMovieCast() != null) {
            this.movieCast = updatedMovie.getMovieCast();
        }
        if (updatedMovie.getMovieReleaseDate() != null) {
            this.movieReleaseDate = updatedMovie.getMovieReleaseDate();
        }
        this.movieFavorite = updatedMovie.isMovieFavorite();
        this.autoplay = updatedMovie.isAutoplay();
    }

    public void setId(Long id) {
    }
}
