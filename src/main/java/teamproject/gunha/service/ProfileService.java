package teamproject.gunha.service;

import teamproject.gunha.vo.ProfileVO;

public interface ProfileService {
  
  boolean updateProfile(ProfileVO profileVO);  
  boolean removeProfile(ProfileVO profileVO);
  boolean createProfile(ProfileVO profileVO);

}
