package teamproject.gunha.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ProfileVO {
  private String userId;
  private String originName;
  private String profileName;
}
