package teamproject.gunha.security.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.security.service.OAuth2UserDetailsService;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

  @Autowired
  private OAuth2UserDetailsService oAuth2UserDetailsService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    // 로그인 시 csrf토큰을 받아야 하는가
    http.csrf().disable();

    http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        .sessionFixation().migrateSession()
        .and()
        .authorizeHttpRequests()
        .antMatchers("/css/**", "/icons/**", "/images/**", "/views/**", "/bootstrap/**").permitAll()
        .antMatchers("/emp/**", "/profile/**", "/my/**", "/payment-card", "/home").hasAnyRole("USER")
        .antMatchers("/admin/**").hasAnyRole("ADMIN")
        .antMatchers("/**", "/login").permitAll()
        .and()
        .exceptionHandling()
        .accessDeniedPage("/");

    http.formLogin()
        .loginPage("/login") // 미인증자일경우 해당 uri를 호출
        .loginProcessingUrl("/login") // login 주소가 호출되면 시큐리티가 낚아 채서(post로 오는것) 대신 로그인 진행 -> 컨트롤러를 안만들어도 된다.
        .successHandler(authenticationSuccessHandler())
        .failureUrl("/login?error");

    http.oauth2Login()
        .loginPage("/login")
        .defaultSuccessUrl("/")
        .userInfoEndpoint()
        .userService(oAuth2UserDetailsService);

    http.logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/");

    return http.build();
  }

  @Bean
  public AuthenticationSuccessHandler authenticationSuccessHandler() {
    return new AuthenticationSuccessHandler();
  }

}

class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  @Override
  protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) {
        Collection<? extends GrantedAuthority> authList = authentication.getAuthorities();
        System.out.println("successHandler()..  " + authList);
    List<String> authStrList = new ArrayList<>();
    authList.stream().forEach(auth -> authStrList.add(auth.getAuthority().toString()));
    if (authStrList.contains("ROLE_ADMIN")) {
      return "/admin";
    } else {
      return "/home";
    }
  }
}
