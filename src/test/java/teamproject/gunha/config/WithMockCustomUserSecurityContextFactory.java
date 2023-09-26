package teamproject.gunha.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import teamproject.gunha.mapper.UserMapper;
import teamproject.gunha.security.config.auth.NetflixUserDetails;
import teamproject.gunha.vo.UserVO;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser>{

  @Autowired
  private UserMapper userMapper;

  @Override
  public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();

    UserVO user = userMapper.selectUserId(customUser.username());

    NetflixUserDetails principal = new NetflixUserDetails(user);

    Authentication auth =
        new UsernamePasswordAuthenticationToken(principal, "password", principal.getAuthorities());

    context.setAuthentication(auth);

    return context;
  }
}
