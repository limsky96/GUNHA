package teamproject.gunha.service;

import java.util.Map;

import teamproject.gunha.vo.ProfileVO;

public interface ProfileService {
  
  boolean updateProfile(ProfileVO profileVO);  
  boolean removeProfile(ProfileVO profileVO);
  boolean createProfile(ProfileVO profileVO);
  boolean addFavorite(ProfileVO profileVO);
  boolean removeFavorite(ProfileVO profileVO);
  boolean updateFavorite(ProfileVO profileVO);

}
