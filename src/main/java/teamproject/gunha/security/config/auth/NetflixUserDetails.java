package teamproject.gunha.security.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import teamproject.gunha.vo.AuthVO;
import teamproject.gunha.vo.UserVO;




/**
 * NetflixUserDetails
 */
public class NetflixUserDetails implements UserDetails {

  private UserVO userVO;
  private List<GrantedAuthority> authorities;

  public NetflixUserDetails(UserVO userVO) {
    this.setAuthorities(userVO);
    this.setUserVO(userVO);
  }

  public void setUserVO(UserVO userVO) {
    this.userVO = userVO;
  }

  public void setAuthorities(UserVO userVO) {
    List<GrantedAuthority> authorities = new ArrayList<>();

      for (AuthVO auth : userVO.getAuthList()) {
         authorities.add(new SimpleGrantedAuthority(auth.getAuthority()));
      }

    this.authorities = authorities;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public String getPassword() {
    return userVO.getPassword();
  }

  @Override
  public String getUsername() {
    return userVO.getUserId();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public String toString(){
    return "NetflixUserDetails[UserVO userVO="+userVO+"], [List<GrantedAuthority> authorities="+authorities+"]";
  }

}