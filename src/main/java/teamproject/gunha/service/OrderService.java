package teamproject.gunha.service;


import java.util.Map;

import teamproject.gunha.vo.PortOneVO;


public interface OrderService {
  Map<String, Object> getAccessToken();
  // Map<String,Object> useAccessToken(String accessToken);
  Map<String, Object> issueBilling(PortOneVO portOneVO, String accessToken);
  Map<String, Object> issueScheduleBilling(PortOneVO portOneVO, String accessToken);

  Map<String, Object> getBillingKey(String cUid);

}
