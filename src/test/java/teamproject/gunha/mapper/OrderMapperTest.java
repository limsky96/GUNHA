package teamproject.gunha.mapper;


import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.vo.OrderVO;

@SpringBootTest
@Slf4j
class OrderMapperTest {
  
  @Autowired
  private OrderMapper orderMapper;
  

  @Test
  void testUserLastOrder(){

    assertNotNull(orderMapper);

    //int last = orderMapper.selectLastOrderId();

    OrderVO lastOrder = orderMapper.selectUserLastOrder("tatelulove4@naver.com_kakao");

    log.info(lastOrder+"");
  }

  @Test
  void testUserOrderDateil(){

    OrderVO orderDetail = orderMapper.selectMemberOrderDetail(1);

    log.info(orderDetail.toString());

  }


}
