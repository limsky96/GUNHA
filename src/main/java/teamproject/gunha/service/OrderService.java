package teamproject.gunha.service;


import java.util.Map;

import teamproject.gunha.vo.PortOneVO;


public interface OrderService {
  Map<String, Object> getAccessToken();
  // Map<String,Object> useAccessToken(String accessToken);
  Map<String, Object> issueBilling(PortOneVO portOneVO);
  Map<String, Object> issueScheduleBilling(PortOneVO portOneVO);

  // Map<String, Object> getPaymentData(Map<String, Object> jsonObject, String accessToken);

  Map<String, Object> issueSchedulePayment(Map<String, Object> jsonObject);

}
