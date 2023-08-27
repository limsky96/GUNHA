package teamproject.gunha.service;

import teamproject.gunha.vo.UserVO;

public interface UserLoginService {

  UserVO getUser(String userId);
  UserVO loginUser(UserVO userDTO);
  boolean createAccount(UserVO userVO);
}
