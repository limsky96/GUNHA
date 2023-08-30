package teamproject.gunha.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import teamproject.gunha.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    // 필요하면 쿼리 메소드 계속 추가
    // 특정 장르의 영화 조회 메소드
    List<Movie> findByMovieGenre(String genre);

    // 모든 영화를 가져오는 메소드
    List<Movie> findAll();
}
