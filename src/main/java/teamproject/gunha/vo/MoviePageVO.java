package teamproject.gunha.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class MoviePageVO {
    private int startIndex;
    private int count;
    private int endIndex;
    
    public int getEndIndex(){
        return startIndex+count;
    }
}
