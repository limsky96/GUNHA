package teamproject.gunha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.mapper.UserMapper;
import teamproject.gunha.security.config.auth.NetflixUserDetails;
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
    return userVO;
    // return userMapper.loginUser(userVO.getUserId(), userVO.getPassword());
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
  public boolean updateAccount(UserVO userVO){
    log.info(userVO.toString());
    // Principal 정보 업데이트 (예: 사용자 이름 변경)
    NetflixUserDetails netflixUserDetails = (NetflixUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    UserVO curUser = netflixUserDetails.getUserVO();
    if(userVO.getUserEmail()==null){
      userVO.setUserEmail(curUser.getUserEmail());
    }
    if(userVO.getPassword()==null || "".equals(userVO.getPassword())){
      userVO.setPassword(curUser.getPassword());
    } else{
      userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));
    }
    if(userVO.getSocial()==null){
      userVO.setSocial(curUser.getSocial());
    }
    if(userVO.getAuthList()==null){
      userVO.setAuthList(curUser.getAuthList());
    }
    if(userVO.getProfileList()==null){
      userVO.setProfileList(curUser.getProfileList());
    }


    netflixUserDetails.setUserVO(userVO);
    userMapper.updateUser(userVO);
    // SecurityContext 업데이트
    Authentication authentication = new UsernamePasswordAuthenticationToken(netflixUserDetails, netflixUserDetails.getPassword(), netflixUserDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);

    return true;
  }



}
