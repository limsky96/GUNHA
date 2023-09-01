package teamproject.gunha.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import teamproject.gunha.vo.MovieVO;

@Mapper
public interface MovieMapper {
    // 필요하면 쿼리 메소드 계속 추가
    // 특정 장르의 영화 조회 메소드
    List<MovieVO> findByMovieGenre(String genre);

    // 모든 영화를 가져오는 메소드
    List<MovieVO> findMovieList();

    boolean existsById(Long id);

    void deleteById(Long id);

    void save(MovieVO movie);

    MovieVO findById(Long id);

    void update(MovieVO movie);

}
