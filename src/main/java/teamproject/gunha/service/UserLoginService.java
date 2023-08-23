package teamproject.gunha.service;

import teamproject.gunha.vo.UserVO;

public interface UserLoginService {

  UserVO getUser(String userId);
  public UserVO loginUser(UserVO userDTO);
}
