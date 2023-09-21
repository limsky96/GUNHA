package teamproject.gunha.service.order;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.mapper.OrderMapper;
import teamproject.gunha.mapper.UserMapper;
import teamproject.gunha.vo.OrderVO;
import teamproject.gunha.vo.PortOneVO;
import teamproject.gunha.vo.UserVO;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderMapper orderMapper;

  private final UserMapper userMapper;

  private final String REST_API_KEY = "3080354631487168";
  private final String REST_API_SECRET = "dOMgxkwCkBv4rlbLPKcAMud3VRe0XNgIKfnCUEGuz83pVvd1aFhCGUQX9fZeD5VfDrSfwUczdZcr2Daw";
  private final String PG_MID = "iamport01m";
  private final String PG = "nice_v2." + PG_MID;
  private final String mUid = "project_netflix_muid_";
  private final String cUid = "_cuid_order_";

  @Override
  public Map<String, Object> getAccessToken() {
    RestTemplate rt = new RestTemplate();

    // HttpHeader 오브젝트 생성
    HttpHeaders requestHeader = new HttpHeaders();
    requestHeader.add("Content-type", "application/json;charset=utf-8");

    // Http Body 오브젝트 생성
    Map<String, Object> requestBody = new HashMap<>();
    String url = "https://api.iamport.kr/users/getToken";
    requestBody.put("imp_key", REST_API_KEY);
    requestBody.put("imp_secret", REST_API_SECRET);

    // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
    HttpEntity<Map<String, Object>> accessTokenRequest = new HttpEntity<>(requestBody, requestHeader);

    log.info(accessTokenRequest.toString());

    Map<String, Object> accessTokenResponse = (Map<String, Object>) rt.exchange(url, HttpMethod.POST,
        accessTokenRequest, Map.class).getBody().get("response");

    log.info(accessTokenResponse.toString());

    return accessTokenResponse;
  }
/*
  @Override
  @Transactional
  public Map<String, Object> issueBilling(PortOneVO portOneVO) {
    log.info("serviceimpl : issuBilling() ... ");
    Map<String, Object> getToken = getAccessToken();
    String accessToken = (String) getToken.get("access_token");
    int lastOrderId = orderMapper.selectLastOrderId();
    String orderId = String.format("%06d", lastOrderId);
    String userOrderId = String.format("%06d", orderMapper.selectUserLastOrderId(portOneVO.getUserId()));
    String merchantUid = mUid + orderId;
    String customerUid = portOneVO.getUserId() + cUid + userOrderId;
    if (portOneVO.getUserId() != null) {
      portOneVO.setMerchantUid(merchantUid);
      portOneVO.setCustomerUid(customerUid);
    }
    log.info(portOneVO.toString());

    RestTemplate rt = new RestTemplate();

    // HttpHeader 오브젝트 생성
    HttpHeaders requestHeader = new HttpHeaders();
    requestHeader.add("Authorization", accessToken);

    // Http Body 오브젝트 생성
    String url = "https://api.iamport.kr/subscribe/customers/" + customerUid;

    Map<String, Object> requestBody = new HashMap<>();

    requestBody.put("merchant_uid", portOneVO.getMerchantUid());
    requestBody.put("customer_uid", portOneVO.getCustomerUid());
    requestBody.put("card_number", portOneVO.getCardNumber());
    requestBody.put("expiry", portOneVO.getExpiry());
    requestBody.put("birth", portOneVO.getBirth());
    requestBody.put("pwd_2digit", portOneVO.getPwd2digit());
    requestBody.put("pg", PG);
    requestBody.put("amount", portOneVO.getAmount());
    requestBody.put("name", portOneVO.getName() + " 구독");
    log.info(requestBody.toString());
    // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
    HttpEntity<Map<String, Object>> billRequest = new HttpEntity<>(requestBody, requestHeader);

    // log.info(accessTokenRequest.toString());
    OrderVO orderVO = OrderVO.builder()
        .memberId(portOneVO.getUserId())
        .cardNumber(portOneVO.getCardNumber())
        .startDate(new Date(new java.util.Date().getTime()))
        .orderValid("T")
        .customerUid(portOneVO.getCustomerUid())
        .impUid("임시imps_uid")
        .build();

    //빌링키 응답 받아온다.
    ResponseEntity<Map> billResponse = rt.exchange(url, HttpMethod.POST,
        billRequest, Map.class);
    log.info("billResponse: " + billResponse);

    Map<String, Object> resp = new HashMap<>();
    resp.put("status", "failed");
    int code = (int) billResponse.getBody().get("code");
    if (code == 0) { // 빌링키 발급 성공
      resp.put("status", "success");
      resp.put("message", "Billing has successfully issued");
      // 빌링키(카드번호와 같은 customer_uid)를 받으면
      // db에 넣고 주문 상태를 T(Temporary)로 놓는다.
      if (orderMapper.insertOrder(orderVO) < 1) {
        resp.put("status", "failed");
        resp.put("message", "DB insert 문제");
      }
    } else { // 빌링키 발급 실패
      resp.put("status", "failed");
      resp.put("message", (String) billResponse.getBody().get("message"));
    }
    return resp;
  }
*/

  @Override
  public Map<String, Object> payOnetime(PortOneVO portOneVO) {
    log.info("payOneTime 첫결제 하고 예약까지.");
    Map<String, Object> getToken = getAccessToken();

    String accessToken = (String) getToken.get("access_token");
    log.info("access_token :" + accessToken);

    log.info("portOneVO: " + portOneVO.toString());
    // 결제 요청해야됨
    String orderId = String.format("%06d", orderMapper.selectNextOrderId());
    String userOrderId = String.format("%06d", orderMapper.selectUserLastOrderId(portOneVO.getUserId()) + 1);
    String merchantUid = mUid + orderId;
    String customerUid = portOneVO.getUserId() + cUid + userOrderId;
    if (portOneVO.getUserId() != null) {
      portOneVO.setMerchantUid(merchantUid);
      portOneVO.setCustomerUid(customerUid);
    }
    log.info(portOneVO.toString());

    // 헤더에 액세스 토큰 추가
    HttpHeaders requestHeader = new HttpHeaders();
    requestHeader.add("Authorization", accessToken);

    Map<String, Object> requestBody = new HashMap<>();

    requestBody.put("merchant_uid", portOneVO.getMerchantUid());
    requestBody.put("customer_uid", portOneVO.getCustomerUid());
    requestBody.put("card_number", portOneVO.getCardNumber());
    requestBody.put("expiry", portOneVO.getExpiry());
    requestBody.put("birth", portOneVO.getBirth());
    requestBody.put("pwd_2digit", portOneVO.getPwd2digit());
    requestBody.put("pg", PG);
    requestBody.put("amount", portOneVO.getAmount());
    requestBody.put("name", portOneVO.getName() + " 구독");
    requestBody.put("buyer_email", userMapper.selectUserId(portOneVO.getUserId()).getUserEmail());

    log.info(requestBody.toString());
    String url = "https://api.iamport.kr/subscribe/payments/onetime";

    RestTemplate rt = new RestTemplate();

    HttpEntity<Map<String, Object>> payRequest = new HttpEntity<>(requestBody, requestHeader);

    // // 결제 요청 후 response 받기
    ResponseEntity<Map> payResponse = rt.exchange(url, HttpMethod.POST,
        payRequest, Map.class);

    log.info(payResponse.toString());
    int code = (int) payResponse.getBody().get("code");
    Map<String, Object> responseData = (Map<String, Object>) payResponse.getBody().get("response");

    Map<String, Object> resp = new HashMap<>();
    resp.put("status", "failed");
    if (code == 0) {
      String impUid = (String) responseData.get("imp_uid");
      OrderVO onetimeOrder = OrderVO.builder()
          .cardNumber(portOneVO.getCardNumber())
          .memberId(portOneVO.getUserId())
          .startDate(new Date(new java.util.Date().getTime()))
          .orderValid("V")
          .customerUid(customerUid)
          .impUid(impUid)
          .build();

      resp.put("status", "success");
      resp.put("message", "Billing has successfully issued");
      int insertRn = orderMapper.insertOrder(onetimeOrder);
      log.info("insertRn : " + insertRn);
      if (insertRn < 1) {
        resp.put("status", "failed");
        resp.put("message", "DB update 문제");
        return resp;
      }
      UserVO userVO = UserVO.builder()
        .userId(portOneVO.getUserId())
        .membershipNo(portOneVO.getMembershipNo())
        .cardNumber(portOneVO.getCardNumber())
        .build();
      log.info(onetimeOrder.toString());
      log.info(userVO.toString());
      userMapper.updateUser(userVO);
      Map<String, Object> jsonObject = new HashMap<>();
      String scheOrderId = String.format("%06d", orderMapper.selectLastOrderId());
      String scheMerchantUid = mUid + scheOrderId;
      jsonObject.put("merchant_uid", scheMerchantUid);
      jsonObject.put("imp_uid", impUid);
      log.info(jsonObject.toString());

    } else {
      resp.put("status", "failed");
      resp.put("message", (String) payResponse.getBody().get("message"));
    }
    return resp;
  }

  public Map<String, Object> getPaymentData(Map<String, Object> jsonObject, String accessToken) {
    // // https://api.iamport.kr/payments/${imp_uid}
    RestTemplate rt = new RestTemplate();
    String impUid = (String) jsonObject.get("imp_uid");
    // // HttpHeader 오브젝트 생성
    HttpHeaders requestHeader = new HttpHeaders();
    requestHeader.add("Authorization", accessToken);

    // // HttpBody 오브젝트 생성
    Map<String, Object> requestBody = new HashMap<>();

    String url = "https://api.iamport.kr/payments/" + impUid;
    // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
    HttpEntity<Map<String, Object>> paymentDataRequest = new HttpEntity<>(requestBody, requestHeader);

    // // log.info(accessTokenRequest.toString());

    ResponseEntity<Map> paymentDataResponse = rt.exchange(url, HttpMethod.GET,
        paymentDataRequest, Map.class);
    Map<String, Object> paymentData = paymentDataResponse.getBody();
    return paymentData;
  }

  @Override
  public Map<String, Object> issueSchedulePayment(Map<String, Object> jsonObject) {

    RestTemplate rt = new RestTemplate();
    Map<String, Object> getToken = getAccessToken();
    String accessToken = (String) getToken.get("access_token");
    String merchantUid = (String) jsonObject.get("merchant_uid");
    Map<String, Object> paymentData = getPaymentData(jsonObject, accessToken);

    Map<String, Object> response = (Map<String, Object>) paymentData.get("response");
    String status = (String) response.get("status");
    String failReason = (String) response.get("fail_reason");
    String impUid = (String) response.get("imp_uid");
    log.info("imp_uid: " + impUid +", merchant_uid: " + merchantUid);
    if ("paid".equals(status) && failReason == null) {
      String[] mList = merchantUid.split("_");
      int orderId = Integer.parseInt(mList[mList.length - 1]);
      long nextStartAt = Timestamp.valueOf(LocalDateTime.now().plusMinutes(3)).getTime();
      Date nextStartDate = new java.sql.Date(nextStartAt);
      nextStartAt /= 1000;


      log.info("orderId: " + orderId);
      OrderVO orderVO = orderMapper.selectOrderByOrderId(orderId);

      log.info("getOrderId(): " + orderVO.getOrderId());
      HttpHeaders requestHeader = new HttpHeaders();
      requestHeader.add("Authorization", accessToken);
      String nextMerchantUid = mUid + String.format("%06d", orderMapper.selectNextOrderId());
      log.info(nextMerchantUid);
      Map<String, Object> requestBody = new HashMap<>();
      Map<String, Object> schedules = new HashMap<>();
      schedules.put("merchant_uid", nextMerchantUid);
      schedules.put("schedule_at", nextStartAt);
      schedules.put("amount", orderVO.getAmount());
      schedules.put("name", orderVO.getMembershipGrade() + " 정기구독");
      schedules.put("currency", "KRW");
      schedules.put("buyer_email", userMapper.selectUserId(orderVO.getMemberId()).getUserEmail());
      List<Map<String, Object>> scheduleList = new ArrayList<>();
      scheduleList.add(schedules);
      requestBody.put("customer_uid", orderVO.getCustomerUid());
      requestBody.put("schedules", scheduleList);

      OrderVO prevOrder = orderMapper.selectUserSecondLastOrder(orderVO.getMemberId());
      prevOrder.setOrderValid("E");
      orderMapper.updateOrder(prevOrder);
      orderVO.setImpUid(impUid);
      orderVO.setOrderValid("V");
      orderMapper.updateOrder(orderVO);
      orderVO = OrderVO.builder()
            .memberId(orderVO.getMemberId())
            .cardNumber(orderVO.getCardNumber())
            .startDate(nextStartDate)
            .orderValid("T")
            .customerUid(orderVO.getCustomerUid())
            .impUid("임시impUid")
            .build();
      HttpEntity<Map<String, Object>> schedulePayRequest = new HttpEntity<>(requestBody, requestHeader);
      String url = "https://api.iamport.kr/subscribe/payments/schedule";
      ResponseEntity<Map> schedulePayResponse = rt.exchange(url, HttpMethod.POST, schedulePayRequest, Map.class);
      orderMapper.insertOrder(orderVO);
      log.info(schedulePayResponse.toString());
    } else {

    }
    return response;
  }

  @Override
  public Map<String, Object> cancelSchedule(Map<String, Object> jsonObject) {
    Map<String, Object> getToken = getAccessToken();
    String accessToken = (String) getToken.get("access_token");
    String userId = (String) jsonObject.get("user_id");


    OrderVO orderVO = orderMapper.selectUserLastOrder(userId);
    
    RestTemplate rt = new RestTemplate();

    orderVO.setOrderValid("C");
    HttpHeaders requestHeader = new HttpHeaders();
    requestHeader.add("Authorization", accessToken);

    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("merchant_uid", orderVO.getMerchantUid());
    requestBody.put("customer_uid", orderVO.getCustomerUid());

    log.info("orderVO: " + orderVO);
    log.info("requestBody: " + requestBody);

    String url = "https://api.iamport.kr/subscribe/payments/unschedule";
    HttpEntity<Map<String, Object>> cancelScheduleRequest = new HttpEntity<>(requestBody, requestHeader);
    ResponseEntity<Map> cancelScheduleResponse = rt.exchange(url, HttpMethod.POST,
        cancelScheduleRequest, Map.class);
    Map<String, Object> cancelScheduleData = (Map<String, Object>) cancelScheduleResponse.getBody();
    int code = (int) cancelScheduleData.get("code");
    log.info("cancelresponseData" + cancelScheduleData);
    Map<String, Object> cancelScheduleDataResponse =null;
    if(cancelScheduleData != null){
      cancelScheduleDataResponse = ((List<Map<String, Object>>) cancelScheduleData.get("response")).get(0);
    }

    Map<String, Object> response = new HashMap<>();
    if (code == 0) {
      String scheduleStatus = (String) cancelScheduleDataResponse.get("schedule_status");
      if ("revoked".equals(scheduleStatus)) {
        int status = 200;
        String message = "멤버십 해지가 정상적으로 완료되었습니다. 다음달부터 멤버십이 해지됩니다.";
        response.put("status", status);
        response.put("message", message);
        orderMapper.deleteOrder(orderVO);
      }
    } else {
      int status = 200;
      response.put("status", status);
      response.put("message", (String) cancelScheduleData.get("message"));
    }
    return response;
  }

}
