package teamproject.gunha.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.mapper.OrderMapper;
import teamproject.gunha.mapper.ProfileMapper;
import teamproject.gunha.mapper.UserMapper;
import teamproject.gunha.security.config.auth.NetflixUserDetails;
import teamproject.gunha.vo.ProfileVO;
import teamproject.gunha.vo.UserVO;

@Service
@Slf4j
public class ProfileServiceImpl implements ProfileService {

  @Autowired
  private ProfileMapper profileMapper;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private OrderMapper orderMapper;


  @Override
  public boolean createProfile(ProfileVO profileVO) {
    log.info(profileVO + "");
    int count = profileMapper.getNumberOfUserProfile(profileVO);
    if (count < 5) {
      int resultRowLine = profileMapper.insertProfile(profileVO);
      if (resultRowLine >= 1) {
        refreshUser();
        return true;
      }

    }
    return false;
  }

  @Override
  @Transactional
  public boolean updateProfile(ProfileVO profileVO) {
    log.info(profileVO + "");
    int resultRowLine = profileMapper.updateProfile(profileVO);
    if (resultRowLine >= 1) {
      refreshUser();
      return true;
    }
    return false;
  }

  @Override
  @Transactional
  public boolean removeProfile(ProfileVO profileVO) {
    int count = profileMapper.getNumberOfUserProfile(profileVO);
    if (count > 1) {
      int resultRowLine = profileMapper.deleteProfile(profileVO);
      if (resultRowLine >= 1) {
        refreshUser();
        return true;
      }
    }
    return false;
  }




  private void refreshUser() {
    NetflixUserDetails prevUserDetails = (NetflixUserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();

    UserVO userVO = userMapper.selectUserId(prevUserDetails.getUsername());
    userVO.setLastOrder(orderMapper.selectUserLastOrder(userVO.getUserId()));
    userVO.setSecondLastOrder(orderMapper.selectUserSecondLastOrder(userVO.getUserId()));
    NetflixUserDetails netflixUserDetails = new NetflixUserDetails(userVO);
    log.info(netflixUserDetails + "");
    Authentication authentication = new UsernamePasswordAuthenticationToken(netflixUserDetails,
        netflixUserDetails.getPassword(), netflixUserDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

}
