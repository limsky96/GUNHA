package teamproject.gunha.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teamproject.gunha.vo.MovieVO;

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

    // @Override
    // public MovieVO get(Long movie_id) {
    // // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method 'get'");
    // }

    // @Override
    // public boolean modify(MovieVO movie) {
    // // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method 'modify'");
    // }

    // @Override
    // public boolean remove(Long movie_id) {
    // // TODO Auto-generated method stub
    // }
}
