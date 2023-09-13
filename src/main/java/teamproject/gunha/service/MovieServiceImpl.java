package teamproject.gunha.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.mapper.MovieMapper;
import teamproject.gunha.vo.MoviePageVO;
import teamproject.gunha.vo.MovieVO;

@Slf4j
@Service
@Transactional
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieMapper movieMapper;

    @Override
    public List<MovieVO> findMovieList(MoviePageVO moviePageVO) {
        if (moviePageVO.getStartIndex() == 0 && moviePageVO.getCount() == 0) {
            return movieMapper.selectMovieList();
        }
        log.info("" + moviePageVO.getStartIndex() + ", " + moviePageVO.getCount());
        return movieMapper.selectPagedMovieList(moviePageVO);
    }

    // @Override
    // public List<MovieVO> getMovieList() {
    // return movieMapper.selectMovieList();
    // }

    // @Override
    // public List<MovieVO> getPagedMovieList(MoviePageVO moviePageVO) {
    // return movieMapper.selectPagedMovieList(moviePageVO);
    // }

    @Override
    public boolean isMovieExists(Long id) {
        return movieMapper.existsById(id);
    }

    @Override
    public void deleteMovie(Long id) {
        movieMapper.deleteById(id);
    }

    @Override
    public void saveMovie(MovieVO movie) {
        movieMapper.save(movie);
    }

    @Override
    public MovieVO getMovieById(Long id) {
        return movieMapper.findById(id);
    }

    @Override
    public void updateMovie(MovieVO movie) {
        movieMapper.update(movie);
    }

    @Override
    public void insertMovie(MovieVO movie) {
        movieMapper.insertMovie(movie);
    }

    @Override
    public List<MovieVO> getAllMovies() {
        return (List<MovieVO>) movieMapper.getAllMovies();
    }
}