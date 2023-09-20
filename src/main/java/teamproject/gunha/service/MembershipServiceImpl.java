package teamproject.gunha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teamproject.gunha.mapper.MembershipMapper;
import teamproject.gunha.mapper.UserMapper;
import teamproject.gunha.vo.MembershipVO;

@Service
public class MembershipServiceImpl implements MembershipService {

  @Autowired
  private MembershipMapper membershipMapper;

  @Autowired
  private UserMapper userMapper;

  @Override
  public MembershipVO getMembership(int membershipNo) {
    // refreshUser();
    return membershipMapper.selectMembership(membershipNo);
  }

  //   private void refreshUser() {
  //   NetflixUserDetails netflixUserDetails = (NetflixUserDetails) SecurityContextHolder.getContext().getAuthentication()
  //       .getPrincipal();
  //   netflixUserDetails.setUserVO(userMapper.selectUserId(netflixUserDetails.getUsername()));
  //   Authentication authentication = new UsernamePasswordAuthenticationToken(netflixUserDetails,
  //       netflixUserDetails.getPassword(), netflixUserDetails.getAuthorities());
  //   SecurityContextHolder.getContext().setAuthentication(authentication);
  // }

}
