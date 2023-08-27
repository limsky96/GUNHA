package teamproject.gunha.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import teamproject.gunha.security.service.NetflixUserDetailsService;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private NetflixUserDetailsService netflixUserDetailsService;

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
  
  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
      .antMatchers("/css/**", "/icons/**", "/images/**",
        "/views/**", "/bootstrap/**", "/mp4/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.csrf().disable();

    http.authorizeHttpRequests()
      .antMatchers("/emp/**").hasAnyRole("USER")
      .antMatchers("/admin/**").hasAnyRole("ADMIN")
      .antMatchers("/").permitAll();
    
    http.formLogin()
    .loginPage("/login").defaultSuccessUrl("/").permitAll();

    http.logout()
      .logoutUrl("/logout")
      .logoutSuccessUrl("/");
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    
    // auth.inMemoryAuthentication()
    // .withUser("user").password("{noop}user").roles("USER")
    // .and()
    // .withUser("admin").password("{noop}admin").roles("ADMIN");
    auth
      .userDetailsService(netflixUserDetailsService)   // 구현해야함
      .passwordEncoder(new BCryptPasswordEncoder());

  }
}


