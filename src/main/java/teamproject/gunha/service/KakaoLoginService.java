package teamproject.gunha.service;

import teamproject.gunha.security.vo.OAuthTokenKakao;

public interface KakaoLoginService {
  public OAuthTokenKakao getOAuthToken(String code);
  public String getKakaoProfile(OAuthTokenKakao oAuthToken) ;
  public boolean logoutKakao(OAuthTokenKakao oAuthToken);
}
