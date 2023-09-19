package teamproject.gunha.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import teamproject.gunha.vo.MembershipVO;

@Mapper
public interface MembershipMapper {
  

  @Select("select membership_no as membershipNo, membership_grade as grade,"
        + " membership_amount as amount from NETFLIX_MEMBERSHIP where membership_no = #{membershipNo}")
  MembershipVO selectMembership(int membershipNo);

}
