package teamproject.gunha.controller.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.security.config.auth.NetflixUserDetails;
import teamproject.gunha.service.MembershipService;
import teamproject.gunha.service.UserLoginService;
import teamproject.gunha.service.order.OrderRestService;
import teamproject.gunha.service.order.OrderService;
import teamproject.gunha.vo.OrderVO;
import teamproject.gunha.vo.PortOneVO;
import teamproject.gunha.vo.UserVO;

@Controller
@Slf4j
public class OrderController {

  @Autowired
  private OrderService orderService;

  @Autowired
  private OrderRestService orderRestService;

  @Autowired
  private UserLoginService userLoginService;

  @Autowired
  private MembershipService membershipService;


  @GetMapping("/payment-card")
  public String paymentCard(
      @AuthenticationPrincipal NetflixUserDetails netflixUserDetails,
      Model model) {
    if (netflixUserDetails != null) {
      UserVO user = netflixUserDetails.getUserVO();
      model.addAttribute("membership", membershipService.getMembership(user.getMembershipNo()));
    }

    return "login/payment-card";
  }

  @GetMapping("/my/order")
  public String orderPage(@AuthenticationPrincipal NetflixUserDetails netflixUserDetails, Model model) {
    if (netflixUserDetails != null) {
      UserVO user = netflixUserDetails.getUserVO();
      userLoginService.loginAccount();
      model.addAttribute("user", netflixUserDetails);
      model.addAttribute("orderList", orderRestService.getUserOrderList(user.getUserId()));
      model.addAttribute("userId", user.getUserId());
    }
    return "order/orderlist";
  }

  // @PostMapping("/subscription/issue-billing")
  @ResponseBody
  public Map<String, Object> subBilling(PortOneVO portOneVO) {

    Map<String, Object> getToken = orderService.getAccessToken();
    log.info(getToken.toString());
    String accessToken = (String) getToken.get("access_token");
    // log.info(paymentService.useAccessToken(accessToken).toString());
    // paymentService.issueBilling(portOneVO, accessToken);

    // return paymentService.issueBilling(portOneVO, accessToken);
    return new HashMap<String, Object>();
  }


  @PostMapping("/subscription/issue-billing")
  @ResponseBody
  public Map<String, Object> scheduleSubscription(PortOneVO portOneVO) {

    Map<String, Object> responseMap = orderService.payOnetime(portOneVO);

    userLoginService.loginAccount();

    return responseMap;
  }

  @PostMapping("/subscription/schedule-alert")
  @ResponseBody
  public Map<String, Object> scheduleAlert(@RequestBody Map<String, Object> jsonObject) {
    log.info(jsonObject.toString());

    orderService.issueSchedulePayment(jsonObject);

    return jsonObject;
  }

  @DeleteMapping("/my/cancel")
  @ResponseBody
  public Map<String, Object> cancelBill(@RequestBody Map<String, Object> jsonObject){
    // String response = (String)jsonObject.get("customer_uid") + ", "  + (String) jsonObject.get("merchant_uid");
    Map<String, Object> response = orderService.cancelSchedule(jsonObject);
    log.info(response.toString());

    return response;
  }

}
