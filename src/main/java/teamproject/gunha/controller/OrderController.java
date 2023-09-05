package teamproject.gunha.controller;

import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.Port;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.service.OrderService;
import teamproject.gunha.vo.PortOneVO;

@Controller
@Slf4j
public class OrderController {

  @Autowired
  private OrderService paymentService;

  @GetMapping("/order")
  public String orderPage() {
    return "order/order-page";
  }

  @PostMapping("/subscription/issue-billing")
  @ResponseBody
  public Map<String, Object> subBilling(PortOneVO portOneVO) {

    Map<String, Object> getToken = paymentService.getAccessToken();
    log.info(getToken.toString());
    String accessToken = (String) getToken.get("access_token");
    // log.info(paymentService.useAccessToken(accessToken).toString());
    // paymentService.issueBilling(portOneVO, accessToken);

    return paymentService.issueBilling(portOneVO, accessToken);
    // return new HashMap<String, Object>();
  }

  @GetMapping(("/get-bill"))
  @ResponseBody
  public Map<String, Object> getBill() {
    Map<String, Object> getToken = paymentService.getAccessToken();
    String accessToken = (String) getToken.get("access_token");
    return paymentService.getBillingKey(accessToken);
  }

}
