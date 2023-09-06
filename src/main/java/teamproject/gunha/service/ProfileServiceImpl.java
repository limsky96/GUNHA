package teamproject.gunha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
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

  @Override
  public boolean createProfile(ProfileVO profileVO) {
    log.info(profileVO + "");
    int resultRowLine = profileMapper.insertProfile(profileVO);
    if (resultRowLine >= 1) {
      refreshUser();
      return true;
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
    int resultRowLine = profileMapper.deleteProfile(profileVO);
    if (resultRowLine >= 1) {
      refreshUser();
      return true;
    }
    return false;
  }

  private void refreshUser() {
    NetflixUserDetails netflixUserDetails = (NetflixUserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
    netflixUserDetails.setUserVO(userMapper.selectUserId(netflixUserDetails.getUsername()));
    Authentication authentication = new UsernamePasswordAuthenticationToken(netflixUserDetails,
        netflixUserDetails.getPassword(), netflixUserDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}
