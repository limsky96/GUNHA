package teamproject.gunha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.mapper.UserMapper;
import teamproject.gunha.vo.UserVO;


@Transactional
@Service
@Slf4j
public class UserLoginServiceImpl implements UserLoginService {

  @Autowired
  private UserMapper userMapper;

  @Override
  public UserVO getUser(String userId) {
    return userMapper.selectUserId(userId);
  }

  @Override
  @Transactional(readOnly = true) // Select 할 때 트랜잭션 시작, 서비스 종료 시에 트랜잭션 종료(정합성)
  public UserVO loginUser(UserVO userVO) {

    return userMapper.loginUser(userVO.getUserId(), userVO.getPassword());
  }

  @Override
  public int createAccount(UserVO userVO){
    log.info(userVO.toString());
    //int rn = userMapper.insertUser(userVO);
    return 1;
  }

}
