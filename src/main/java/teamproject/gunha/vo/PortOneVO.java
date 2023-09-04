package teamproject.gunha.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PortOneVO {
  private String cardNumber;
  private String expiry;
  private String birth;
  private String pwd2digit;
  private String customerUid;
}
