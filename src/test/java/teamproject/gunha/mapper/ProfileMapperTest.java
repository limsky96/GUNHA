package teamproject.gunha.mapper;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.vo.ProfileVO;

@SpringBootTest
@Slf4j
public class ProfileMapperTest {
  
  @Autowired
  private ProfileMapper profileMapper;

  @Test
  public void testGetUserProfile(){

    assertNotNull(profileMapper.getProfileList());
    List<ProfileVO> profileList = profileMapper.getProfileList();

    profileList.stream().forEach(elmnt ->{
      log.info(elmnt.toString());
    });

  }


  


}
