package teamproject.gunha.service.order.task;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.mapper.OrderMapper;
import teamproject.gunha.vo.OrderVO;

@Component
@Slf4j
public class OrderValidTask {

  @Autowired
  private OrderMapper orderMapper;

  @Scheduled(cron = "0 */1 * * * ?")
  public void membershipValidingTask() {
    List<OrderVO> orderList = orderMapper.selectOrderList();

    orderList.stream().filter(t->!t.getOrderValid().equals("E")).forEach(order -> {

      // 현재 시간의 Timestamp 객체 생성
      Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

      // Timestamp 객체에서 LocalDateTime으로 변환
      LocalDateTime currentLocalDateTime = currentTimestamp.toLocalDateTime();

      // 연도, 월, 일 정보 가져오기
      int year = currentLocalDateTime.getYear();
      int month = currentLocalDateTime.getMonthValue();
      int day = currentLocalDateTime.getDayOfMonth();

      // 연도, 월, 일 정보를 사용하여 LocalDateTime 객체 생성
      LocalDateTime today = LocalDateTime.of(year, month, day, 0, 0);

      log.info(order.getStartDate().toString());
      Date now = new Date(Timestamp.valueOf(today).getTime());

      if (order.getStartDate().compareTo(now) <= 0) {
        order.setOrderValid("E");
      }
      orderMapper.updateOrder(order);
    });

  }

}

class ExampleCompare implements Comparable {
  private int num;

  @Override
  public int compareTo(Object o) {
    int result = num - ((ExampleCompare) o).num;
    return result;
  }

  public int getNum() {
    return num;
  }

  public void setNum(int num) {
    this.num = num;
  }
}