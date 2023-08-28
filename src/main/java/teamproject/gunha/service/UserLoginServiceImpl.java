package teamproject.gunha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.mapper.UserMapper;
import teamproject.gunha.vo.ProfileVO;
import teamproject.gunha.vo.UserVO;


@Service
@Slf4j
public class UserLoginServiceImpl implements UserLoginService {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Override
  public UserVO getUser(String userId) {
    return userMapper.selectUserId(userId);
  }

  @Override
  public UserVO loginUser(UserVO userVO) {
    return userMapper.loginUser(userVO.getUserId(), userVO.getPassword());
  }

  @Override
  @Transactional // insert 할 때 트랜잭션 시작, 서비스 종료 시에 트랜잭션 종료(정합성)
  public boolean createAccount(UserVO userVO){
    userVO.setUserEmail(userVO.getUserId());
    userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));
    userVO.setSocial("none");
    ProfileVO defaultProfile = new ProfileVO(userVO.getUserId(), "테스트");
    userMapper.insertUser(userVO);
    userMapper.insertAuthorities(userVO);
    userMapper.insertProfile(defaultProfile);

    return true;
  }

  @Override
  @Transactional // insert 할 때 트랜잭션 시작, 서비스 종료 시에 트랜잭션 종료(정합성)
  public boolean signupSocial(UserVO userVO){
    userMapper.updateUser(userVO);
    
    return true;
  }



}
