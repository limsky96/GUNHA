package teamproject.gunha.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminMovieServiceImpl implements AdminMovieService {

    @Autowired
    private AdminMovieMapper adminMovieMapper;


    @Override
    public List<AdminMovieVO> getAllMovies() {
        return adminMovieMapper.getAllMovies();
    }

    @Override
    public AdminMovieVO getMovieById(int movieId) {
        return adminMovieMapper.getMovieById(movieId);
    }

    @Override
    public void saveMovie(AdminMovieVO movie) {
        adminMovieMapper.saveMovie(movie);
    }

    @Override
    public void deleteMovie(int movieId) {
        adminMovieMapper.deleteMovie(movieId);
    }
}
