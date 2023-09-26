package teamproject.gunha.mapper;

import org.apache.tomcat.jni.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.vo.UserVO;

@SpringBootTest
@Slf4j
class UserMapperTest {
  
  @Autowired
  private UserMapper userMapper;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;


  @Test
  @Transactional
  @DisplayName("유저 정보 수정")
  void testUserUpdate(){
    log.info("userupdate test");


    UserVO userVO = UserVO.builder()
                .userId("tatelulove4@naver.com")
                .userEmail("tatelulove4@naver.com")
                .password(passwordEncoder.encode("Dldnjsrjs4$4"))
                .cardNumber("1234-1234-1234-1234")
                .membershipNo(0)
                .social("none")
                .build();


    log.info("result Number : " + userMapper.updateUser(userVO));


  }


}
