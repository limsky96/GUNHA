package teamproject.gunha.service.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import teamproject.gunha.vo.OrderVO;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderRestSerivceImpl implements OrderRestService{

  private final OrderMapper orderMapper;

  private final OrderService orderService;

  @Override
  public List<Map<String, Object>> getUserOrderList(String userId) {
    List<OrderVO> orderList = orderMapper.selectUserOrderList(userId);
    List<Map<String, Object>> orderJsonList = new ArrayList<>();
    if(orderList != null){
      orderList.stream().forEach(order->{
        Map<String, Object> orderJson = new HashMap<>();
        Map<String, Object> responseData = (Map<String,Object>) getOrderDetail(order);
        int code = (int)responseData.get("code");
        if(code == 0){
          orderJson.put("orderId", order.getOrderId());
          orderJson.put("orderData", responseData.get("response"));
          orderJsonList.add(orderJson);
        }
        // if(responseData != null){
        //   orderJson.put(String.valueOf(order.getOrderId()), responseData);
        //   orderJsonList.add(orderJson);
        // }
      });
    }
    return orderJsonList;
  }

  public Map<String, Object> getOrderDetail(OrderVO order){
    String impUid = order.getImpUid();

    String accessToken = (String) orderService.getAccessToken().get("access_token");
    RestTemplate rt = new RestTemplate();

    // HttpHeader 오브젝트 생성
    HttpHeaders requestHeader = new HttpHeaders();
    requestHeader.add("Authorization", accessToken);

    // Http Body 오브젝트 생성
    Map<String, Object> requestBody = new HashMap<>();
    String url = "https://api.iamport.kr/payments/" + impUid;

    // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
    HttpEntity<Map<String, Object>> orderDetailRequest = new HttpEntity<>(requestBody, requestHeader);

    Map<String,Object> orderDetailResponse = (Map<String, Object>) rt.exchange(url, HttpMethod.GET,
        orderDetailRequest, Map.class).getBody();
    log.info(orderDetailResponse.toString());
    return orderDetailResponse;
  } 
  
}
