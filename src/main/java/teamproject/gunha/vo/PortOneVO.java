package teamproject.gunha.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PortOneVO {
  private String userId;
  private String merchantUid;
  private double amount;
  private String cardNumber;
  private String expiry;
  private String birth;
  private String pwd2digit;
  private String customerUid;
  private String name;
  
}
