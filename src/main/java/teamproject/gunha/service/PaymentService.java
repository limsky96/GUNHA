package teamproject.gunha.service;


import java.util.Map;

import teamproject.gunha.vo.PortOneVO;


public interface PaymentService {
  Map<String, Object> getAccessToken();
  // Map<String,Object> useAccessToken(String accessToken);
  Map<String, Object> issueBilling(PortOneVO portOneVO, String accessToken);
}
