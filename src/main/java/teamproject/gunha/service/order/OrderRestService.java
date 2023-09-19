package teamproject.gunha.service.order;

import java.util.List;
import java.util.Map;

import teamproject.gunha.vo.OrderVO;

public interface OrderRestService {
    List<Map<String, Object>> getUserOrderList(String userId);

}
