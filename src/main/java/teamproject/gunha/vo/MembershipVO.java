package teamproject.gunha.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class MembershipVO {
  private int membershipNp;
  private String grade;
  private int amount; 
}
