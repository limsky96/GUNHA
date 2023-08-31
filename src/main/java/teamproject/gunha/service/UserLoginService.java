package teamproject.gunha.service;

import teamproject.gunha.vo.UserVO;

public interface UserLoginService {

  UserVO getUser(String userId);
  UserVO loginUser(UserVO userVO);
  boolean createAccount(UserVO userVO);
  boolean updateAccount(UserVO userVO);
}
