package teamproject.gunha.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import teamproject.gunha.vo.OrderVO;

@Mapper
public interface OrderMapper {
  
  @Insert("insert into netflix_order(order_id, order_member_id, order_member_card_number,"
        + " order_start_date, order_valid, order_customer_uid)"
        + " values((select nvl(max(order_id),0)+1 from netflix_order), #{memberId},"
        + " #{cardNumber}, #{startDate}, #{orderValid}, #{customerUid})")
  public int insertOrder(OrderVO orderVO);
  
  public int selectLastOrderId();

  public OrderVO selectOrderByOrderId(int orderId);

  public int selectUserLastOrderId(String userId);

  public OrderVO selectUserLastOrder(String userId);

  @Update("update netflix_order set order_start_date = #{startDate}, order_valid=#{orderValid}"
        + " where order_id = #{orderId}") 
  public int updateOrder(OrderVO orderVO);

  public OrderVO selectMemberOrderDetail(int orderId);


}
