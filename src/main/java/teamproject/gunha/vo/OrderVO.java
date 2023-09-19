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
//  ORDER_CUSTOMER_UID                        NOT NULL varchar2(80)
//  

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class OrderVO {
  private int orderId;
  private String memberId;
  private String cardNumber;
  private Date startDate;
  private String orderValid;
  private String customerUid;
  private String membershipGrade;
  private String amount;
  private String merchantUid;
  private String impUid;

  public String getMerchantUid(){
    return "project_netflix_muid_" + String.format("%06d", orderId);
  }

  
}
