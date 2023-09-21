package teamproject.gunha.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.mapper.FavoriteMapper;
import teamproject.gunha.vo.FavoriteVO;

@Service
@Slf4j
public class FavoriteServiceImpl implements FavoriteService {

  @Autowired
  private FavoriteMapper favoriteMapper;

  @Override
  public List<Integer> getUserFavList(FavoriteVO favoriteVO){
    List<FavoriteVO> favoriteList = favoriteMapper.selectUserFavList(favoriteVO);

    List<Integer> favMovieIdList = new ArrayList<>();
    for(FavoriteVO favorite: favoriteList){
      int movieId = favorite.getMovieId();
      favMovieIdList.add(movieId);
    }
    return favMovieIdList;
  }


  @Override
  public boolean addFavorite(FavoriteVO favoriteVO) {
    List<FavoriteVO> favoriteList = favoriteMapper.selectUserFavList(favoriteVO);
    for(FavoriteVO favorite : favoriteList){
      if(favorite.getMovieId() == favoriteVO.getMovieId()){
        return false;
      }
    }
    int resultRows = favoriteMapper.insertFavorite(favoriteVO);
    if (resultRows >= 1) {
      return true;
    }
    return false;
  }

  @Override
  public boolean removeFavorite(FavoriteVO favoriteVO) {
        List<FavoriteVO> favoriteList = favoriteMapper.selectUserFavList(favoriteVO);
    for(FavoriteVO favorite : favoriteList){
      if(favorite.getMovieId() == favoriteVO.getMovieId()){
        return false;
      }
    }
    int resultRows = favoriteMapper.deleteFavorite(favoriteVO);
    if (resultRows >= 1) {
      return true;
    }
    return false;
  }

  @Override
  public boolean updateFavorite(FavoriteVO favoriteVO) {
    // TODO Auto-generated method stub
    return false;
  }

}
