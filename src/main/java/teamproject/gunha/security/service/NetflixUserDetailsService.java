package teamproject.gunha.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import teamproject.gunha.mapper.OrderMapper;
import teamproject.gunha.mapper.UserMapper;
import teamproject.gunha.security.config.auth.NetflixUserDetails;
import teamproject.gunha.vo.UserVO;

@Service
@Log4j2
public class NetflixUserDetailsService implements UserDetailsService {
  
  @Autowired
  private UserMapper userMapper;

  @Autowired
  private OrderMapper orderMapper;


  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    log.info(userId);
    UserVO user = userMapper.selectUserId(userId);
    log.warn("queried by UserVO mapper: " + user);
    log.warn("orderMapper:  " + orderMapper.selectUserLastOrder(userId));
    user.setLastOrder(orderMapper.selectUserLastOrder(userId));
    user.setSecondLastOrder(orderMapper.selectUserSecondLastOrder(userId));
    if("none".equals(user.getSocial())){
      NetflixUserDetails netflixUserDetails = new NetflixUserDetails(user);
      return netflixUserDetails; // 시큐리티 세션에 유저 정보 저장
    }
    return null;
  }
  
}
