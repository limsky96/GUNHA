package teamproject.gunha.mapper;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.vo.FavoriteVO;
import teamproject.gunha.vo.ProfileVO;


@SpringBootTest
@Slf4j
class FavoriteMapperTest {
  
  @Autowired
  private FavoriteMapper favoriteMapper;

  @Test
  @Transactional
  @DisplayName("내가 찜한 컨텐츠에 추가")
  void testInsertFavorite(){

    ProfileVO profileVO = ProfileVO.builder()
              .userId("tatelulove4@naver.com_kakao")
              .profileName("테스트")
              .build();

    int id = 10;

    List<FavoriteVO> favList = favoriteMapper.selectUserFavList(profileVO);

    for(FavoriteVO fav : favList){
      log.info(fav.toString());
      assertNotEquals(id, fav.getMovieId());
      if(fav.getMovieId() == id){
        log.info("같은 movie id가 존재. 오류");
        return;
      }
    }

    FavoriteVO favoriteVO = FavoriteVO.builder()
                  .userId(profileVO.getUserId())
                  .profileName(profileVO.getProfileName())
                  .movieId(id)
                  .build();

    log.info("insert result rows : " + favoriteMapper.insertFavorite(favoriteVO));

    favList = favoriteMapper.selectUserFavList(profileVO);
    favList.stream().forEach(fav->{
      log.info(fav.toString());
    });
    
  }

}
