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
    
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    // Principal 정보 업데이트 (예: 사용자 이름 변경)
    NetflixUserDetails updatedPrincipal = (NetflixUserDetails) authentication.getPrincipal();
    updatedPrincipal.setUserVO(userMapper.selectUserId(userVO.getUserId()));

    // SecurityContext 업데이트
    Authentication newAuthentication = new UsernamePasswordAuthenticationToken(updatedPrincipal, authentication.getCredentials(), authentication.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(newAuthentication);

    return true;
  }



}
