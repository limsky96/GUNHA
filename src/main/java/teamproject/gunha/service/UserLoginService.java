package teamproject.gunha.service;

import java.util.Map;

import teamproject.gunha.security.config.auth.NetflixUserDetails;
import teamproject.gunha.vo.UserVO;

public interface UserLoginService {

  UserVO getUser(String userId);
  NetflixUserDetails loginAccount();
  NetflixUserDetails loginAccount(Map<String, Object> jsonObject);
  boolean createAccount(UserVO userVO);
  boolean modifyAccount(Map<String, Object> json, UserVO userVO);
  boolean memberCheckAndLogin(Map<String, Object> requestBody);
  Map<String,Object> changeUserPassword(Map<String, Object> jsonObject);
}
