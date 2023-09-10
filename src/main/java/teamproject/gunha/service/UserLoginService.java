package teamproject.gunha.service;

import teamproject.gunha.vo.UserVO;

public interface UserLoginService {

  UserVO getUser(String userId);
  boolean loginAccount(UserVO userVO);
  boolean createAccount(UserVO userVO);
  boolean modifyAccount(UserVO userVO);
}
