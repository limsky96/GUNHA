package teamproject.gunha.controller;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.security.config.auth.NetflixUserDetails;
import teamproject.gunha.service.OrderService;
import teamproject.gunha.vo.PortOneVO;
import teamproject.gunha.vo.UserVO;

@Controller
@Slf4j
public class OrderController {

  @Autowired
  private OrderService paymentService;

  @GetMapping("/order")
  public String orderPage(@AuthenticationPrincipal NetflixUserDetails netflixUserDetails, Model model) {
    if(netflixUserDetails == null) return "order/order-page";
    UserVO user = netflixUserDetails.getUserVO();
    model.addAttribute("userId", user.getUserId());

    return "order/order-page";
  }

  // @PostMapping("/subscription/issue-billing")
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

  @PostMapping("/subscription/issue-billing")
  @ResponseBody
  public Map<String, Object> subscribePass(PortOneVO portOneVO) {
    Map<String, Object> getToken = paymentService.getAccessToken();
    log.info(getToken.toString());
    String accessToken = (String) getToken.get("access_token");
    // log.info(paymentService.useAccessToken(accessToken).toString());
    paymentService.issueBilling(portOneVO, accessToken);

    return paymentService.issueScheduleBilling(portOneVO, accessToken);
  }

  @PostMapping("/subscription/schedule")
  @ResponseBody
    public Map<String, Object> scheduleSubscription(PortOneVO portOneVO) {
    Map<String, Object> getToken = paymentService.getAccessToken();
    log.info(getToken.toString());
    String accessToken = (String) getToken.get("access_token");
    // log.info(paymentService.useAccessToken(accessToken).toString());
    paymentService.issueBilling(portOneVO, accessToken);

    return paymentService.issueScheduleBilling(portOneVO, accessToken);
  }


  @PostMapping("/subscription/schedule-alert")
  @ResponseBody
    public Map<String, Object> scheduleAlert(@RequestBody Map<String,Object> jsonObject) {
    log.info(jsonObject.toString());
    String impUid = (String)jsonObject.get("imp_uid");
    log.info("imp_uid: " +impUid );
    return jsonObject;
  }


}
