package teamproject.gunha.service.order;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import teamproject.gunha.mapper.OrderMapper;
import teamproject.gunha.vo.OrderVO;

@Service
@RequiredArgsConstructor
public class OrderRestSerivceImpl implements OrderRestService{

  private final OrderMapper orderMapper;

  @Override
  public List<OrderVO> getUserOrderList(String userId) {
    return orderMapper.selectUserOrderList(userId);
  }

  
  
}
