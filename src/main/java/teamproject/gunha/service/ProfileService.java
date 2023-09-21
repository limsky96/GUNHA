package teamproject.gunha.service;

import java.util.Map;

import teamproject.gunha.vo.ProfileVO;

public interface ProfileService {
  
  boolean updateProfile(ProfileVO profileVO);  
  boolean removeProfile(ProfileVO profileVO);
  boolean createProfile(ProfileVO profileVO);


}
