package teamproject.gunha.security.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamproject.gunha.vo.AuthVO;
import teamproject.gunha.vo.UserVO;

/**
 * NetflixUserDetails
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NetflixUserDetails implements UserDetails, OAuth2User {

  private UserVO userVO;
  private String selectedProfile;
  private List<GrantedAuthority> authorities;
  private Map<String, Object> attributes;

  public NetflixUserDetails(UserVO user) {
    this.setUserVO(user);
    this.setAuthorities(user);
  }

  public NetflixUserDetails(UserVO user, Map<String, Object> attributes) {
    this.setUserVO(user);
    this.setAuthorities(user);
    this.setAttributes(attributes);
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
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @Override
  public String getName() {
    return null;
  }
}