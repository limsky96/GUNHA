package teamproject.gunha.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieVO {
    private Long movieId;
    private String movieName;
    private String movieContent;
    private String movieGenre;
    private String movieCast;
    private Date movieReleaseDate;
    private boolean movieFavorite;
}
