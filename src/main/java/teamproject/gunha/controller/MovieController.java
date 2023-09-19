package teamproject.gunha.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import teamproject.gunha.service.MovieService;
import teamproject.gunha.vo.MoviePageVO;
import teamproject.gunha.vo.MovieVO;

@Controller
@Slf4j
@RequestMapping("/movies")
public class MovieController {

    // Mapper는 service에서 불러올 것
    // MovieRepository : 데이터베이스와 상호작용하여 영화 정보를 가져오는 역할
    private MovieService movieService; // MovieRepository 인터페이스의 구현체를 주입받음

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/api/movies") // api/movies (GET) 메소드 호출
    @ResponseBody
    public List<MovieVO> getMovies(MoviePageVO moviePageVO) { // 영화 목록 가져오는 메소드
        log.info("" + moviePageVO.getStartIndex() + ", " + moviePageVO.getCount());
        // return movieMapper.findMovieList(); // DB에 저장된 모든 영화 정보를 가져온 후, 리스트로 변환
        return movieService.findMovieList(moviePageVO);
    }

    @GetMapping("/api/movies/{id}") // api/movies (GET) 메소드 호출
    @ResponseBody
    public MovieVO getMovies(@PathVariable int id) { // 영화 목록 가져오는 메소드
        log.info(id + "");
        // return movieMapper.findMovieList(); // DB에 저장된 모든 영화 정보를 가져온 후, 리스트로 변환
        return movieService.getOneMovie(id);
    }

    // 모든 영화 목록 가져오기
    @GetMapping("/all")
    public ResponseEntity<List<MovieVO>> getAllMovies() {
        List<MovieVO> movies = movieService.getAllMovies();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    // 페이징된 영화 목록 가져오기
    @GetMapping("/paged")
    public ResponseEntity<List<MovieVO>> getPagedMovies(@RequestParam(defaultValue = "0") int startIndex,
            @RequestParam(defaultValue = "10") int count) {
        MoviePageVO moviePageVO = new MoviePageVO(startIndex, count);
        List<MovieVO> movies = movieService.findMovieList(moviePageVO);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    // 특정 영화 가져오기
    @GetMapping("/{id}")
    public ResponseEntity<MovieVO> getMovieById(@PathVariable Long id) {
        MovieVO movie = movieService.getMovieById(id);
        if (movie != null) {
            return new ResponseEntity<>(movie, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 새로운 영화 추가
    @PostMapping("/add")
    public ResponseEntity<Void> addMovie(@RequestBody MovieVO movie) {
        movieService.insertMovie(movie);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 영화 정보 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMovie(@PathVariable Long id, @RequestBody MovieVO movie) {
        movie.setId(id);
        if (movieService.isMovieExists(id)) {
            movieService.updateMovie(movie);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 영화 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        if (movieService.isMovieExists(id)) {
            movieService.deleteMovie(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // CRUD
    // @PostMapping  
    // public ResponseEntity<MovieVO> createMovie(@RequestBody MovieVO newMovie) {
    // movieMapper.save(newMovie);
    // return ResponseEntity.status(HttpStatus.CREATED).body(newMovie);
    // }

    // 단일 영화 정보 조회 기능
    // @GetMapping("/{id}")
    // public ResponseEntity<MovieVO> getMovie(@PathVariable Long id) {
    // MovieVO movie = movieMapper.findById(id);
    // if (movie != null) {
    // return ResponseEntity.ok(movie);
    // } else {
    // return ResponseEntity.notFound().build();
    // }
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<MovieVO> updateMovie(@PathVariable Long id,
    // @RequestBody MovieVO updatedMovie) {
    // MovieVO movie = movieMapper.findById(id);
    // if (movie != null) {
    // movie.updateFrom(updatedMovie);
    // movieMapper.update(movie);
    // return ResponseEntity.ok(movie);
    // } else {
    // return ResponseEntity.notFound().build();
    // }
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
    // if (movieMapper.existsById(id)) {
    // movieMapper.deleteById(id);
    // return ResponseEntity.noContent().build();
    // } else {
    // return ResponseEntity.notFound().build();
    // }
    // }
}