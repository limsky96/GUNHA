package teamproject.gunha.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import teamproject.gunha.mapper.MovieMapper;
import teamproject.gunha.vo.MovieVO;

@RestController // 엔드포인트 URL
public class MovieController {

    // MovieRepository : 데이터베이스와 상호작용하여 영화 정보를 가져오는 역할
    @Autowired
    private MovieMapper movieMapper; // MovieRepository 인터페이스의 구현체를 주입받음

    @GetMapping("/api/movies") // api/movies (GET) 메소드 호출
    public List<MovieVO> getMovies() { // 영화 목록 가져오는 메소드
        return movieMapper.findMovieList(); // DB에 저장된 모든 영화 정보를 가져온 후, 리스트로 변환
    }
}