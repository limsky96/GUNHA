package teamproject.gunha.mapper;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.vo.ProfileVO;

@SpringBootTest
@Slf4j
class ProfileMapperTest {

  @Autowired
  private ProfileMapper profileMapper;

  @Test
  @Transactional
  @DisplayName("프로필 정보 불러오기")
  void testGetUserProfile() {

    assertNotNull(profileMapper);
    List<ProfileVO> profileList = profileMapper.getUserProfiles("tatelulove4@naver.com_kakao");

    profileList.stream().forEach(elmnt -> {
      log.info(elmnt.toString());
    });

  }

  @Test
  @Transactional
  @DisplayName("프로필 정보 생성하기")
  void testInsertUserProfile() {
    assertNotNull(profileMapper);

    List<ProfileVO> profileList = profileMapper.getUserProfiles("tatelulove4@naver.com_kakao");

    profileList.stream().forEach(elmnt -> {
      log.info(elmnt.toString());
    });

    ProfileVO profileVO = new ProfileVO("tatelulove4@naver.com_kakao", null, "테스트프로필명");

    profileMapper.insertProfile(profileVO);

    profileList = profileMapper.getUserProfiles(profileVO.getUserId());

    profileList.stream().forEach(elmnt -> {
      log.info(elmnt.toString());
    });

  }

}
