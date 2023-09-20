package teamproject.gunha.service.order;


import java.util.List;
import java.util.Map;

import teamproject.gunha.vo.PortOneVO;


public interface OrderService {
  Map<String, Object> getAccessToken();
  // Map<String,Object> useAccessToken(String accessToken);
  Map<String, Object> issueBilling(PortOneVO portOneVO);
  Map<String, Object> issueScheduleBilling(PortOneVO portOneVO);

  // Map<String, Object> getPaymentData(Map<String, Object> jsonObject, String accessToken);

  Map<String, Object> issueSchedulePayment(Map<String, Object> jsonObject);

  Map<String, Object> cancelSchedule(Map<String, Object> jsonObject);

}
