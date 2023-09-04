package teamproject.gunha.service;

import java.util.HashMap;
import java.util.Map;

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
public class PaymentServiceImpl implements PaymentService{

  private String REST_API_KEY = "3080354631487168";
  private String REST_API_SECRET = "dOMgxkwCkBv4rlbLPKcAMud3VRe0XNgIKfnCUEGuz83pVvd1aFhCGUQX9fZeD5VfDrSfwUczdZcr2Daw";


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

		Map<String, Object> accessTokenResponse =(Map<String, Object>) rt.exchange(url, HttpMethod.POST,
				accessTokenRequest, Map.class).getBody().get("response");

    log.info(accessTokenResponse.toString());
    
    return accessTokenResponse;
  }

  @Override
  public Map<String, Object> issueBiling(PortOneVO portOneVO, String accessToken) {
    RestTemplate rt = new RestTemplate();

		// HttpHeader 오브젝트 생성
		HttpHeaders requestHeader = new HttpHeaders();
		requestHeader.add("Authorization", accessToken);

		// Http Body 오브젝트 생성
		Map<String, Object> requestBody = new HashMap<>();
    String url = "https://api.iamport.kr/subscribe/customers/"+ portOneVO.getCustomerUid();
    requestBody.put("imp_key", REST_API_KEY);
    requestBody.put("imp_secret", REST_API_SECRET);

		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<Map<String, Object>> accessTokenRequest = new HttpEntity<>(requestBody, requestHeader);

    log.info(accessTokenRequest.toString());

		Map<String, Object> accessTokenResponse =(Map<String, Object>) rt.exchange(url, HttpMethod.POST,
				accessTokenRequest, Map.class).getBody().get("response");

    log.info(accessTokenResponse.toString());
    
    return null;
  }

}
