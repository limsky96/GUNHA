package teamproject.gunha.model;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;
    private String movieName;
    private String movieContent;
    private String movieGenre;
    private String movieCast;
    private Date movieReleaseDate;
    private boolean movieFavorite;
}
