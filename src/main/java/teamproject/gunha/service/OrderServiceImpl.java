package teamproject.gunha.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.mapper.OrderMapper;
import teamproject.gunha.vo.PortOneVO;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderMapper orderMapper;

  private String REST_API_KEY = "3080354631487168";
  private String REST_API_SECRET = "dOMgxkwCkBv4rlbLPKcAMud3VRe0XNgIKfnCUEGuz83pVvd1aFhCGUQX9fZeD5VfDrSfwUczdZcr2Daw";
  private String PG_MID = "iamport01m";
  private String PG = "nice_v2." + PG_MID;
  private String mUid = "project_netflix_muid_";
  private String cUid = "_cuid_order_";

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

  @Override
  public Map<String, Object> issueBilling(PortOneVO portOneVO, String accessToken) {
    String orderId = String.format("%06d", orderMapper.selectNextOrderId());
    String merchantUid = mUid + orderId;
    String customrUid = portOneVO.getUserId()+"_cuid_order_" + orderId;
    portOneVO.setMerchantUid(merchantUid);
    portOneVO.setCustomerUid(customrUid);
    log.info(portOneVO.toString());
    
    RestTemplate rt = new RestTemplate();
    
    
    // HttpHeader 오브젝트 생성
    HttpHeaders requestHeader = new HttpHeaders();
    requestHeader.add("Authorization", accessToken);
    
    // Http Body 오브젝트 생성
    String url = "https://api.iamport.kr/subscribe/payments/onetime";

    Map<String, Object> requestBody = new HashMap<>();
    // card_number, // 카드 번호
    // expiry, // 카드 유효기간
    // birth, // 생년월일
    // pwd_2digit, // 카드 비밀번호 앞 두자리
    // pg: YOUR_PG_HERE, // 빌링키 발급에 사용할 PG
    requestBody.put("merchant_uid", portOneVO.getMerchantUid());
    requestBody.put("customer_uid", portOneVO.getCustomerUid());
    requestBody.put("card_number", portOneVO.getCardNumber());
    requestBody.put("expiry", portOneVO.getExpiry());
    requestBody.put("birth", portOneVO.getBirth());
    requestBody.put("pwd_2digit", portOneVO.getPwd2digit());
    requestBody.put("pg", PG);
    requestBody.put("amount", 100);
    requestBody.put("name", "베이직 구독");
    log.info(requestBody.toString());
    // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
    HttpEntity<Map<String, Object>> billingCodeRequest = new HttpEntity<>(requestBody, requestHeader);

    // log.info(accessTokenRequest.toString());

    // ResponseEntity<Map> billingCodeResponse = rt.exchange(url, HttpMethod.POST,
    //     billingCodeRequest, Map.class);

    // log.info(billingCodeResponse.toString());

    Map<String, Object> resp = new HashMap<>();
    resp.put("status", "failed");
    // int code = (int) billingCodeResponse.getBody().get("code");
    // if (code == 0) { // 빌링키 발급 성공
    //   resp.put("status", "success");
    //   resp.put("message", "Billing has successfully issued");
    // } else { // 빌링키 발급 실패
    //   resp.put("status", "failed");
    //   resp.put("message", (String) billingCodeResponse.getBody().get("message"));
    // }
    return resp;
  }

  @Override
  public Map<String, Object> issueScheduleBilling(PortOneVO portOneVO, String accessToken) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map<String, Object> getBillingKey(String accessToken) {
    // https://api.iamport.kr/subscribe/customers/{customer_uid}
    RestTemplate rt = new RestTemplate();

    // HttpHeader 오브젝트 생성
    HttpHeaders requestHeader = new HttpHeaders();
    requestHeader.add("Authorization", accessToken);

    // Http Body 오브젝트 생성
    Map<String, Object> requestBody = new HashMap<>();
    String cUid = "customer_uid_000004";
    String url = "https://api.iamport.kr/subscribe/customers/"+cUid;
    String mUid = "netofuri_muid_000002";
    // card_number, // 카드 번호
    // expiry, // 카드 유효기간
    // birth, // 생년월일
    // pwd_2digit, // 카드 비밀번호 앞 두자리
    // pg: YOUR_PG_HERE, // 빌링키 발급에 사용할 PG
    requestBody.put("customer_uid", cUid);
    requestBody.put("pg", PG);
    log.info(requestBody.toString());
    // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
    HttpEntity<Map<String, Object>> billingKeyRequest = new HttpEntity<>(requestBody, requestHeader);

    // log.info(accessTokenRequest.toString());

    ResponseEntity<Map> billingKeyResponse = rt.exchange(url, HttpMethod.GET,
        billingKeyRequest, Map.class);

    log.info(billingKeyResponse.toString());
    return null;
  }

}
