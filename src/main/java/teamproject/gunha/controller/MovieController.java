package teamproject.gunha.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.service.MovieService;
import teamproject.gunha.vo.MoviePageVO;
import teamproject.gunha.vo.MovieVO;

@Controller
@Slf4j
public class MovieController {

    // Mapper는 service에서 불러올 것
    // MovieRepository : 데이터베이스와 상호작용하여 영화 정보를 가져오는 역할
    @Autowired
    private MovieService movieService; // MovieRepository 인터페이스의 구현체를 주입받음

    @GetMapping("/api/movies") // api/movies (GET) 메소드 호출
    @ResponseBody
    public List<MovieVO> getMovies(MoviePageVO moviePageVO) { // 영화 목록 가져오는 메소드
        log.info("" + moviePageVO.getStartIndex() + ", " + moviePageVO.getCount());
        // return movieMapper.findMovieList(); // DB에 저장된 모든 영화 정보를 가져온 후, 리스트로 변환
        return movieService.findMovieList(moviePageVO);
    }

    // CRUD
    // @PostMapping
    // public ResponseEntity<MovieVO> createMovie(@RequestBody MovieVO newMovie) {
    //     movieMapper.save(newMovie);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(newMovie);
    // }

    // 단일 영화 정보 조회 기능
    // @GetMapping("/{id}")
    // public ResponseEntity<MovieVO> getMovie(@PathVariable Long id) {
    //     MovieVO movie = movieMapper.findById(id);
    //     if (movie != null) {
    //         return ResponseEntity.ok(movie);
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<MovieVO> updateMovie(@PathVariable Long id, @RequestBody MovieVO updatedMovie) {
    //     MovieVO movie = movieMapper.findById(id);
    //     if (movie != null) {
    //         movie.updateFrom(updatedMovie);
    //         movieMapper.update(movie);
    //         return ResponseEntity.ok(movie);
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
    //     if (movieMapper.existsById(id)) {
    //         movieMapper.deleteById(id);
    //         return ResponseEntity.noContent().build();
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }
}