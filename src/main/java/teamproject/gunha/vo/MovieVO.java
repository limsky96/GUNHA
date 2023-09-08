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
    }
}
