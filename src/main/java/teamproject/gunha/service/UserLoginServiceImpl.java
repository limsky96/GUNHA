package teamproject.gunha.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.mapper.OrderMapper;
import teamproject.gunha.mapper.ProfileMapper;
import teamproject.gunha.mapper.UserMapper;
import teamproject.gunha.security.config.auth.NetflixUserDetails;
import teamproject.gunha.vo.ProfileVO;
import teamproject.gunha.vo.UserVO;

@Service
@Slf4j
public class UserLoginServiceImpl implements UserLoginService {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private ProfileMapper profileMapper;

  @Autowired
  private OrderMapper orderMapper;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Override
  public UserVO getUser(String userId) {
    return userMapper.selectUserId(userId);
  }

  @Override
  @Transactional
  public boolean memberCheckAndLogin(Map<String, Object> requestBody) {
    log.info("memberCheckAndLogin():   ");
    String userId = (String)requestBody.get("user_id");
    String password =(String) requestBody.get("password");
    UserVO user = userMapper.selectUserId(userId);
    log.info("user: " + user);
    if (user != null) {
      if(passwordEncoder.matches(password, user.getPassword())){
        loginAccount();
        return true;
      } else{
        return false;
      }
    } else {
      UserVO newUser = UserVO.builder()
            .userId(userId)
            .password(password).build();
      // 계정 만들기
      createAccount(newUser);
      // 이후 유저 로그인됨
      log.info(newUser.toString());
      loginAccount();
    }
    return true;
  }

  @Override
  public NetflixUserDetails loginAccount() {
    NetflixUserDetails prevUserDetails= (NetflixUserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
    UserVO userVO = userMapper.selectUserId(prevUserDetails.getUsername());
    userVO.setLastOrder(orderMapper.selectUserLastOrder(userVO.getUserId()));
    userVO.setSecondLastOrder(orderMapper.selectUserSecondLastOrder(userVO.getUserId()));
    userVO.setSelectedProfile(prevUserDetails.getUserVO().getSelectedProfile());
    NetflixUserDetails netflixUserDetails = new NetflixUserDetails(userVO);
    log.info(netflixUserDetails+"");
    Authentication authentication = new UsernamePasswordAuthenticationToken(netflixUserDetails,
        netflixUserDetails.getPassword(), netflixUserDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return netflixUserDetails;
  }

  @Override
  public NetflixUserDetails loginAccount(Map<String, Object> jsonObject) {
    NetflixUserDetails prevUserDetails= (NetflixUserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
    UserVO userVO = userMapper.selectUserId(prevUserDetails.getUsername());
    userVO.setLastOrder(orderMapper.selectUserLastOrder(userVO.getUserId()));
    userVO.setSecondLastOrder(orderMapper.selectUserSecondLastOrder(userVO.getUserId()));
    userVO.setSelectedProfile(prevUserDetails.getUserVO().getSelectedProfile());
    String selectedProfile = (String) jsonObject.get("selected_name");
    if(selectedProfile != null){
      userVO.setSelectedProfile(selectedProfile);
    }
    NetflixUserDetails netflixUserDetails = new NetflixUserDetails(userVO);
    log.info(netflixUserDetails+"");
    Authentication authentication = new UsernamePasswordAuthenticationToken(netflixUserDetails,
        netflixUserDetails.getPassword(), netflixUserDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return netflixUserDetails;
  }

  @Override
  @Transactional // insert 할 때 트랜잭션 시작, 서비스 종료 시에 트랜잭션 종료(정합성)
  public boolean createAccount(UserVO userVO) {
    userVO.setUserEmail(userVO.getUserId());
    userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));
    userVO.setCardNumber("결제정보 없음");
    userVO.setMembershipNo(0);
    userVO.setSocial("none");
    ProfileVO defaultProfile = ProfileVO.builder()
        .userId(userVO.getUserId())
        .profileName("테스트")
        .build();
    userMapper.insertUser(userVO);
    userMapper.insertAuthorities(userVO);
    profileMapper.insertProfile(defaultProfile);
    return true;
  }

  @Override
  @Transactional // update 할 때 트랜잭션 시작, 서비스 종료 시에 트랜잭션 종료(정합성)
  public boolean modifyAccount(Map<String, Object> json, UserVO userVO) {
    log.info(json.toString());  
    log.info(userVO.toString());
    // Principal 정보 업데이트 (예: 사용자 이름 변경)
    NetflixUserDetails netflixUserDetails = (NetflixUserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
    UserVO curUser = netflixUserDetails.getUserVO();
    userVO.setUserId(curUser.getUserId());
    if (userVO.getUserEmail() == null) {
      userVO.setUserEmail(curUser.getUserEmail());
    }
    if (userVO.getPassword() == null || userVO.getPassword().equals(curUser.getPassword()) || "".equals(userVO.getPassword())) {
      userVO.setPassword(curUser.getPassword());
    } else {
      userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));
    }
    if(userVO.getMembershipNo() == 0 || json.get("membership_no") != null){
      log.info(json.get("membership_no").toString());
      int membershipNo = Integer.valueOf((String)json.get("membership_no"));
      userVO.setMembershipNo(membershipNo);
    }
    if (userVO.getSocial() == null) {
      userVO.setSocial(curUser.getSocial());
    }
    if (userVO.getAuthList() == null) {
      userVO.setAuthList(curUser.getAuthList());
    }
    if (userVO.getProfileList() == null) {
      userVO.setProfileList(curUser.getProfileList());
    }

    netflixUserDetails.setUserVO(userVO);
    log.info(userVO.toString());
    userMapper.updateUser(userVO);
    // SecurityContext 업데이트
    loginAccount();
    // Authentication authentication = new UsernamePasswordAuthenticationToken(netflixUserDetails,
    //     netflixUserDetails.getPassword(), netflixUserDetails.getAuthorities());
    // SecurityContextHolder.getContext().setAuthentication(authentication);

    return true;
  }

  @Override
  @Transactional // update 할 때 트랜잭션 시작, 서비스 종료 시에 트랜잭션 종료(정합성)
  public Map<String,Object> changeUserPassword(Map<String, Object> jsonObject) {
    log.info(jsonObject.toString());  

    String userId = (String)jsonObject.get("userId");
    String oldPassword = (String)jsonObject.get("oldPassword");
    String newPassword = (String) jsonObject.get("newPassword");
    UserVO userVO = userMapper.selectUserId(userId);
    Map<String, Object> responseData = new HashMap<>();
    if(userId.equals(userVO.getUserId())){
      if(passwordEncoder.matches(oldPassword, userVO.getPassword())){
        userVO.setPassword(passwordEncoder.encode(newPassword));
        userMapper.updateUser(userVO);
        responseData.put("code", 0);
        responseData.put("message", "비밀번호가 성공적으로 변경되었습니다.");
        loginAccount();
      } else{
        responseData.put("code", 1);
        responseData.put("message","기존 비밀번호가 일치하지 않습니다.");
      }
    }
    // SecurityContext 업데이트


    return responseData;
  }

}
