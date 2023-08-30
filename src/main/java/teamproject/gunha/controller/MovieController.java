package teamproject.gunha.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import teamproject.gunha.model.Movie;
import teamproject.gunha.repository.MovieRepository;

@RestController
@RequestMapping("/api/movies") // 엔드포인트 URL
public class MovieController {

    // MovieRepository : 데이터베이스와 상호작용하여 영화 정보를 가져오는 역할
    private final MovieRepository movieRepository; // MovieRepository 인터페이스의 구현체를 주입받음

    @Autowired // 생성자 주입 > MovieRepository의 인스턴스를 컨트롤러에 주입
    public MovieController(MovieRepository movieRepository) { // 생성자 정의
        this.movieRepository = movieRepository; // 인스턴스 초기화
    }

    @GetMapping("/api/movies") // api/movies (GET) 메소드 호출
    public List<Movie> getMovies() { // 영화 목록 가져오는 메소드
        return movieRepository.findAll(); // DB에 저장된 모든 영화 정보를 가져온 후, 리스트로 변환
    }
}