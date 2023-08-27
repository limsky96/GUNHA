package teamproject.gunha.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    userVO.setSocial("none");
    List<ProfileVO> profileList = new ArrayList<>();
    ProfileVO defaultProfile = new ProfileVO(userVO.getUserId(), "테스트");
    profileList.add(defaultProfile);
    userVO.setProfileList(profileList);
    log.info("profile list: " + profileList);
    // userMapper.insertUser(userVO);
    // userMapper.insertAuthorities(userVO);
    // userMapper.insertProfile(userVO);
    log.info("create Account : " + userVO);

    return true;
  }

}
