package teamproject.gunha.service.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import teamproject.gunha.mapper.OrderMapper;
import teamproject.gunha.vo.OrderVO;

@Service
@RequiredArgsConstructor
public class OrderRestSerivceImpl implements OrderRestService{

  private final OrderMapper orderMapper;

  private final OrderService orderService;

  @Override
  public List<OrderVO> getUserOrderList(String userId) {
    List<OrderVO> orderList = orderMapper.selectUserOrderList(userId);
    List<Map<String, Object>> orderJsonList = new ArrayList<>();
    orderList.stream().forEach(order->{
      Map<String, Object> orderJson = new HashMap<>();
      Map<String, Object> responseData = (Map<String,Object>) getOrderDetail(order).get("response");
      orderJson.put(String.valueOf(order.getOrderId()), responseData);
      orderJsonList.add(orderJson);
    });
    return orderList;
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

    Map<String, Object> orderDetailResponse = (Map<String, Object>) rt.exchange(url, HttpMethod.GET,
        orderDetailRequest, Map.class).getBody();
    return orderDetailResponse;
  } 
  
}
