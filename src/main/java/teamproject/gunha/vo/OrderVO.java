package teamproject.gunha.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//  Name                                      Null?    Type
//  ----------------------------------------- -------- ----------------------------
//  ORDER_ID                                  NOT NULL NUMBER
//  ORDER_MEMBER_ID                           NOT NULL VARCHAR2(60)
//  ORDER_MEMBER_CARD_NUMBER                           CHAR(19)
//  ORDER_START_DATE                                   DATE
//  ORDER_VALID                                        CHAR(1)

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class OrderVO {
  private int orderId;
  private String memberId;
  private String cardNumber;
  private Date startDate;
  private String orderValid;
}
