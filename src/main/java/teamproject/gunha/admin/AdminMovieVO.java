package teamproject.gunha.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class AdminMovieVO {

    private int movieId;
    private String movieName;
    private String moviePostUrl;
    private String movieTrailerUrl;
    private String movieContent;
    private String movieGenre;
    private String movieCast;
    private Date movieReleaseDate;
    private boolean movieFavorite;
    private String autoplay;
}
