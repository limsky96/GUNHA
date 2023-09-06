package teamproject.gunha.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import teamproject.gunha.vo.OrderVO;

@Mapper
public interface OrderMapper {
  
  @Insert("insert into netflix_order(order_id, order_member_id, order_member_card_number, order_start_date)"
        + " values((select nvl(max(order_id),0)+1 from netflix_order), #{memberId}, #{cardNumber}, #{startDate})")
  public int insertOrder(OrderVO orderVO);
  
  public int selectLastOrderId();

  public int selectUserLastOrderId(String userId);

}
