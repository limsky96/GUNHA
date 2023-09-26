package teamproject.gunha.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import teamproject.gunha.vo.ProfileVO;

@Mapper
public interface ProfileMapper {
  
  public List<ProfileVO> getUserProfiles(String userId);

  public int getNumberOfUserProfile(ProfileVO profileVO);

  @Insert("insert into NETFLIX_MEMBER_PROFILE(member_profile_member_id, member_profile_name)"
        + " values(#{userId}, #{profileName})")
  public int insertProfile(ProfileVO profileVO);

    
  @Update("update NETFLIX_MEMBER_PROFILE set member_profile_name = #{profileName}"
        + " where member_profile_member_id = #{userId} and member_profile_name = #{originName}")
  public int updateProfile(ProfileVO profileVO);

  @Delete("delete from NETFLIX_MEMBER_PROFILE"
        + " where member_profile_member_id = #{userId} and member_profile_name = #{profileName}")
  public int deleteProfile(ProfileVO profileVO);

}
