package teamproject.gunha.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.vo.FavoriteVO;
import teamproject.gunha.vo.ProfileVO;


@SpringBootTest
@Slf4j
class FavoriteMapperTest {
  
  @Autowired
  private FavoriteMapper favoriteMapper;

  @Test
  void testInsertFavorite(){
    FavoriteVO favoriteVO = FavoriteVO.builder()
                  .userId("tatelulove4@naver.com_kakao")
                  .profileName("테스트")
                  .movieId(1)
                  .build();

    log.info("insert result rows : " + favoriteMapper.insertFavorite(favoriteVO));


    
  }

}
