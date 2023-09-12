package teamproject.gunha.service;

import java.util.List;

import teamproject.gunha.vo.MoviePageVO;
import teamproject.gunha.vo.MovieVO;

public interface MovieService {
    List<MovieVO> findMovieList(MoviePageVO moviePageVO);
}
