package teamproject.gunha.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import teamproject.gunha.vo.OrderVO;

@Mapper
public interface OrderMapper {

  public int insertOrder(OrderVO orderVO);
  
  public int selectLastOrderId();

  public int selectNextOrderId();

  public OrderVO selectOrderByOrderId(int orderId);

  public int selectUserLastOrderId(String userId);

  public OrderVO selectUserSecondLastOrder(String userId);

  public OrderVO selectUserLastOrder(String userId);

  public List<OrderVO> selectUserOrderList(String userId);

  public List<OrderVO> selectOrderList();

  public int updateOrder(OrderVO orderVO);

  public int deleteOrder(OrderVO orderVO);


}
