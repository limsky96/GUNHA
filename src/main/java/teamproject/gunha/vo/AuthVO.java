package teamproject.gunha.vo;


import lombok.*;


@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class AuthVO {
  private String userId;
  private String authority;
}
