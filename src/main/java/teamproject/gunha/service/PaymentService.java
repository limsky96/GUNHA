package teamproject.gunha.service;


import java.util.Map;

import teamproject.gunha.vo.PortOneVO;


public interface PaymentService {
  Map<String, Object> getAccessToken();
  Map<String, Object> issueBiling(PortOneVO portOneVO, String accessToken);
}
