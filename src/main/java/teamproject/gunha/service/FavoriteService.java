package teamproject.gunha.service;

import java.util.List;

import teamproject.gunha.vo.FavoriteVO;

public interface FavoriteService {

  List<Integer> getUserFavList(FavoriteVO favoriteVO);
  boolean addFavorite(FavoriteVO favoriteVO);
  boolean removeFavorite(FavoriteVO favoriteVO);
  boolean updateFavorite(FavoriteVO favoriteVO);

}
