package teamproject.gunha.vo;

import java.util.List;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserVO {
  private String userId;
  private String userEmail;
  private String password;
  private String cardNumber;
  private int membershipNo;
  private String social;
  private List<AuthVO> authList;

}
