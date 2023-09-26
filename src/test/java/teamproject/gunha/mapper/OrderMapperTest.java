package teamproject.gunha.mapper;


import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.vo.OrderVO;

@SpringBootTest
@Slf4j
class OrderMapperTest {
  
  @Autowired
  private OrderMapper orderMapper;
  

  @Test
  @Transactional
  @DisplayName("유저의 마지막 주문 정보")
  void testUserLastOrder(){

    assertNotNull(orderMapper);

    //int last = orderMapper.selectLastOrderId();

    OrderVO lastOrder = orderMapper.selectUserLastOrder("tatelulove4@naver.com_kakao");

    log.info(lastOrder+"");
  }

  @Test
  @Transactional
  @DisplayName("주문번호로 주문 정보 불러오기")
  void testUserOrderDetail(){

    OrderVO orderDetail = orderMapper.selectOrderByOrderId(310);

    log.info(orderDetail.toString());

  }

  @Test
  void testSelectLastOrderId(){
    log.info(orderMapper.selectLastOrderId()+"");
  }

    @Test
  void testSelectNextOrderId(){
    log.info(orderMapper.selectNextOrderId()+"");
  }


}
