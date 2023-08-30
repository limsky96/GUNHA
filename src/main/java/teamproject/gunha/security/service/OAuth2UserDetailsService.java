package teamproject.gunha.security.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.mapper.UserMapper;
import teamproject.gunha.security.config.auth.GoogleUserInfo;
import teamproject.gunha.security.config.auth.KakaoUserInfo;
import teamproject.gunha.security.config.auth.NaverUserInfo;
import teamproject.gunha.security.config.auth.NetflixUserDetails;
import teamproject.gunha.security.config.auth.OAuth2UserInfo;
import teamproject.gunha.vo.ProfileVO;
import teamproject.gunha.vo.UserVO;

@Service
@Slf4j
public class OAuth2UserDetailsService extends DefaultOAuth2UserService {

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private UserMapper userMapper;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

    OAuth2User oAuth2User = super.loadUser(userRequest);
    OAuth2UserInfo oAuth2UserInfo = null;

    if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
      oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
    } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
      oAuth2UserInfo = new NaverUserInfo((Map<String, Object>) oAuth2User.getAttributes().get("response"));
    } else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
      oAuth2UserInfo = new KakaoUserInfo((Map<String, Object>) oAuth2User.getAttributes().get("kakao_account"),
          String.valueOf(oAuth2User.getAttributes().get("id")));
    } else {
      System.out.println("지원하지 않은 로그인 서비스 입니다.");
    }

    String provider = oAuth2UserInfo.getProvider(); // google , naver, facebook etc
    String email = oAuth2UserInfo.getEmail();
    
    String userId = email + "_" + provider;
    log.info(userId);
    String password = bCryptPasswordEncoder.encode("user"); // 중요하지 않음 그냥 패스워드 암호화 하
    
    
    // Role role = Role.USER;
    UserVO userVO = userMapper.selectUserId(userId);
    log.info("OAuth method() -> userVO: " + userVO);
    //log.info("password: " + bCryptPasswordEncoder.encode("2577013424"));
    // 처음 서비스를 이용한 회원일 경우
    if (userVO == null) {
      
      userVO = UserVO.builder()
          .userId(userId)
          .userEmail(email)
          .password(password)
          .cardNumber("결제정보 없음")
          .membershipNo(0)
          .social(provider)
          .build();
          
      log.info("builded userVO : " + userVO);
      ProfileVO profileVO = ProfileVO.builder()
          .userId(userId)
          .profileName("테스트")
          .build();

      log.info("builded profileVO : " + profileVO);
      userMapper.insertUser(userVO);
      userMapper.insertAuthorities(userVO);
      userMapper.insertProfile(profileVO);
    }
    log.info(new NetflixUserDetails(userVO).toString());
    return new NetflixUserDetails(userVO);
  }
}
