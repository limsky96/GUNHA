package teamproject.gunha.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

import teamproject.gunha.vo.FavoriteVO;
import teamproject.gunha.vo.ProfileVO;

@Mapper
public interface FavoriteMapper {
  
  @Insert("insert into NETFLIX_FAVORITES(favorites_member_id, favorites_member_profile_name, favorites_movie_id)"
        + " values(#{userId}, #{profileName}, #{movieId})")
  public int insertFavorite(FavoriteVO FavoriteVO);

  public List<FavoriteVO> selectUserFavList(ProfileVO profileVO);


  @Delete("Delete from NETFLIX_FAVORITES where favorites_member_id=${userId} and"
        + " favorites_member_profile_name = #{profileName} and favorites_movie_id = #{movieId}")
  public int deleteFavorite(FavoriteVO FavoriteVO);

}
