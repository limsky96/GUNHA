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

  public int updateUser(UserVO userVO);


}