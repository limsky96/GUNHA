package teamproject.gunha.service.order;

import java.util.List;

import teamproject.gunha.vo.OrderVO;

public interface OrderRestService {
    List<OrderVO> getUserOrderList(String userId);

}
