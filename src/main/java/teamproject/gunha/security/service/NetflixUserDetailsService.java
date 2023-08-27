package teamproject.gunha.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import teamproject.gunha.mapper.UserMapper;
import teamproject.gunha.security.config.auth.NetflixUserDetails;
import teamproject.gunha.vo.UserVO;

@Service
@Log4j2
public class NetflixUserDetailsService implements UserDetailsService {
  
  @Autowired
  private UserMapper userMapper;

  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    
    UserVO user = userMapper.selectUserId(userId);
    log.warn("queried by UserVO mapper: " + user);
    if("none".equals(user.getSocial())){
      
      return new NetflixUserDetails(user); // 시큐리티 세션에 유저 정보 저장
    }
    return null;
  }
  
}