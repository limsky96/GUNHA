package teamproject.gunha.mapper;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.vo.MovieVO;

@SpringBootTest
@Slf4j
class MovieMapperTest {
  
  @Autowired
  private MovieMapper movieMapper;


  @Test
  @Transactional
  @DisplayName("id값으로 영화 데이터 삭제")
  void testDeleteMovie(){
    
    log.info("id값으로 영화 데이터 삭제");

    long id = 5;
    MovieVO movieVO = movieMapper.findById(id);
    
    assertNotNull(movieVO);
    log.info("id: " + id + ", movieVO: " + movieVO );

    movieMapper.deleteById(id);

    movieVO = movieMapper.findById(id);
    
    assertNull(movieVO);
    
    if(movieVO == null){
      log.info("id: " + id + ", movieVO는 null입니다.");
    } else{
      log.info("id: " + id + ", movieVO: " + movieVO.toString());
    }

  }


}
