package teamproject.gunha.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import teamproject.gunha.vo.UserVO;


@Mapper
public interface UserMapper {

  UserVO selectUserId(String userId); 

  @Insert("insert into NETFLIX_MEMBER(member_id, member_email, member_password, member_card_number, member_membership_no, member_social) "
        + "values(#{userId}, #{userEmail}, #{password}, #{cardNumber}, #{membershipNo}, #{social})")
  public int insertUser(UserVO userVO);

  @Insert("insert into NETFLIX_AUTH(auth_member_id, auth_member_authority) values(#{userId},'ROLE_USER')")
  public int insertAuthorities(UserVO userVO);


  @Update("update NETFLIX_MEMBER set member_email=#{userEmail}, member_password=#{password}, "
        + "member_card_number=#{cardNumber}, member_membership_no=#{membershipNo} where member_id=#{userId}")
  public int updateUser(UserVO userVO);


}