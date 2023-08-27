package teamproject.gunha.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import teamproject.gunha.vo.UserVO;


@Mapper
public interface UserMapper {

  UserVO selectUserId(String userId);
	
	@Select("select * from netflix_member where member_id = #{userId} and member_password=#{password}")
  UserVO loginUser(String userId, String password);

  @Insert("insert into netflix_member(member_id, member_email, member_password, member_card_number, member_membership_no, member_social )"
    + " values(#{userId}, #{userEmail}, #{password}, #{cardNumber}, #{membershipNo}, #{social})")
  public int insertUser(UserVO userVO);

  @Insert("insert into netflix_auth (auth_member_id, auth_member_authority) values(#{userId},'ROLE_USER')")
  public void insertAuthorities(UserVO UserVO);

  @Insert("insert into NETFLIX_MEMBER_PROFILE(member_profile_member_id, member_profile_name), values(#{userId}, #{profileName})")
  public void insertProfile(UserVO userVO);

}