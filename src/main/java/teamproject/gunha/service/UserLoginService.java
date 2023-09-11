package teamproject.gunha.service;

import java.util.Map;

import teamproject.gunha.vo.UserVO;

public interface UserLoginService {

  UserVO getUser(String userId);
  boolean loginAccount(UserVO userVO);
  boolean createAccount(UserVO userVO);
  boolean modifyAccount(Map<String, Object> json, UserVO userVO);
  boolean memberCheckAndLogin(Map<String, Object> requestBody);
}
