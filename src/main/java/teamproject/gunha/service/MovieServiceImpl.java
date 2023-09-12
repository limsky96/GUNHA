package teamproject.gunha.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.mapper.MovieMapper;
import teamproject.gunha.vo.MoviePageVO;
import teamproject.gunha.vo.MovieVO;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService{

    @Autowired
    private MovieMapper movieMapper;

    @Override
    public List<MovieVO> findMovieList(MoviePageVO moviePageVO) {
        if(moviePageVO.getStartIndex() == 0 && moviePageVO.getCount() == 0){
            return movieMapper.selectMovieList();
        }
        log.info("" + moviePageVO.getStartIndex() + ", " + moviePageVO.getCount());
        return movieMapper.selectPagedMovieList(moviePageVO);
    }

    @Override
    public MovieVO getOneMovie(int id) {
        log.info("serviceimpl::getOneMovie");
        return movieMapper.findById((long)id);
    }

}
