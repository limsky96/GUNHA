package teamproject.gunha.controller.order;


import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import teamproject.gunha.service.order.OrderRestService;

@Controller
@RequiredArgsConstructor
// @RequestMapping("/order/*")
public class OrderRestController {

  private final OrderRestService orderRestService;


}
