package teamproject.gunha.vo;

import java.util.List;

import lombok.*;


@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UserVO {
  private String userId;
  private String userEmail;
  private String password;
  private String cardNumber;
  private int membershipNo;
  private String social;
  private List<AuthVO> authList;
  private List<ProfileVO> profileList;

}
