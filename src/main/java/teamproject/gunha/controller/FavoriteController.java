package teamproject.gunha.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.service.FavoriteService;
import teamproject.gunha.service.MovieService;
import teamproject.gunha.vo.FavoriteVO;
import teamproject.gunha.vo.MovieVO;

@RestController
@Slf4j
@RequestMapping("/api/fav")
public class FavoriteController {
  
  @Autowired
  private FavoriteService favoriteService;

  @Autowired
  private MovieService movieService;

  @PostMapping({"", "/"})
  public List<MovieVO> getFavMovieData(@RequestBody FavoriteVO favoriteVO){
    List<Integer> favMovieIdList = favoriteService.getUserFavList(favoriteVO);
    
    log.info("favMovieIdList: " + favMovieIdList.toString());
    List<MovieVO> movieList = new ArrayList<>();

    for(int favMovieId : favMovieIdList){
      movieList.add(movieService.getMovieById((long)favMovieId));
    }
    log.info("movieList: " + movieList.toString());
    return movieList;

  }

}
