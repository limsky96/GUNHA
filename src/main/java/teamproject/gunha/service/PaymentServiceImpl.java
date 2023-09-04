package teamproject.gunha.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.vo.PortOneVO;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

  private String REST_API_KEY = "3080354631487168";
  private String REST_API_SECRET = "dOMgxkwCkBv4rlbLPKcAMud3VRe0XNgIKfnCUEGuz83pVvd1aFhCGUQX9fZeD5VfDrSfwUczdZcr2Daw";
  private String PG_MID = "iamport01m";
  private String PG = "nice_v2."+PG_MID;


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
    RestTemplate rt = new RestTemplate();
    
    // HttpHeader 오브젝트 생성
    HttpHeaders requestHeader = new HttpHeaders();
    requestHeader.add("Authorization", accessToken);
    
    log.info(portOneVO.toString());
    // Http Body 오브젝트 생성
    Map<String, Object> requestBody = new HashMap<>();
    String url = "https://api.iamport.kr/subscribe/payments/onetime";
    String uuid = UUID.randomUUID().toString();

    // card_number, // 카드 번호
    // expiry, // 카드 유효기간
    // birth, // 생년월일
    // pwd_2digit, // 카드 비밀번호 앞 두자리
    // pg: YOUR_PG_HERE, // 빌링키 발급에 사용할 PG
    requestBody.put("card_number", portOneVO.getCardNumber());
    requestBody.put("expiry", portOneVO.getExpiry());
    requestBody.put("birth", portOneVO.getBirth());
    requestBody.put("pwd_2digit", portOneVO.getPwd2digit());
    requestBody.put("pg", PG);

    log.info(requestBody.toString());
    // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
    HttpEntity<Map<String, Object>> billingCodeRequest = new HttpEntity<>(requestBody, requestHeader);

    // log.info(accessTokenRequest.toString());

    ResponseEntity<Map> billingCodeResponse = rt.exchange(url, HttpMethod.POST,
        billingCodeRequest, Map.class);

    log.info(billingCodeResponse.toString());

    Map<String, Object> resp = new HashMap<>();
    
    int code = (int) billingCodeResponse.getBody().get("code");
    if (code == 0) { // 빌링키 발급 성공
      resp.put("status", "success");
      resp.put("message", "Billing has successfully issued");
    } else { // 빌링키 발급 실패
      resp.put("status", "failed");
      resp.put("message", (String) billingCodeResponse.getBody().get("message"));
    }
    return resp;
  }

}
