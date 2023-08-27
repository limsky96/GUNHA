<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="/js/jquery/3.6.4/jquery-3.7.0.min.js"></script>
<script src="https://kit.fontawesome.com/cc89ddadc0.js" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.css" />
<link rel="stylesheet" href="/css/home.css"/>

<title>NETFLIX</title>
</head>
<script>
  
  $(document).ready(function() {
    fnInit();  
  });

  function fnInit(){
    
    //임시 TITLE 증가값
    var count = 1;
    //무한스크롤 START
    //DOCUMENT 스크롤 이벤트 감지
    $(document).scroll(function(){
      //문서전체 높이값
      var maxHeight = $(document).height();
      //현재 스크롤 위치
      var currentScroll = $(window).scrollTop() + $(window).height();

      if (maxHeight <= currentScroll + 50) {
        //무한스크롤 END
        //화면에 추가로 보여줄 요소 생성 (AJAX) START
        //category Class내에 추가시킬 html소스 생성 위한 String 변수
        var htmlText = "";
        for(var i=0; i<3; i++){
          htmlText+= '<div class="title" class="fstScl">스크롤컨텐츠'+count+'</div>';
          htmlText+= '<div class="list" class="fstScl">';
          htmlText+= '<div class="movie">';
          htmlText+= '<img src="https://source.unsplash.com/224x126/?random" alt="">';
          htmlText+= '</div>';
          htmlText+= '<div class="movie">';
          htmlText+= '<img src="https://source.unsplash.com/224x126/?random" alt="">';
          htmlText+= '</div>';
          htmlText+= '</div>';
          count++;
        }
        $("#category").append(htmlText);
        //화면에 추가로 보여줄 요소 생성 (AJAX) END
            
      //ajax 대충예시 START
      /*ajax.success(result){
        var titleList = result.titleList;
        var srcList = ruesult.srcList;
        //+[{title:1, src:"xxxx"},{title:1, src:"xxxx"},{title:2, src:"xxxx"},{title:2, src:"xxxx"}];

        if(titleList.length > 0){
           for(var i=0; i<titleList.length; i++){
          htmlText+= '<div class="title" class="fstScl">'+titleList[i]+'</div>';
          htmlText+= '<div class="list" class="fstScl">';
          for(var j=0; j<srcList.length; j++){
            if(titleList[i].title == srcList[j].title){
              htmlText+= '<div class="movie">';
              htmlText+= '<img src="'+srcList[j].src+'" alt="">';
              htmlText+= '</div>';
            }
          }
          htmlText+= '</div>';
        }
        $("#category").append(htmlText); 
        }
        
      }
      , error(error){
        alert('리스트를 불러오는데 실패했습니다.');
      }*/
      //ajax 대충예시 END
      }  
    })



    $("#btnMovePlay").click(function(){
      var srcCd = $("#mainVideo").val();
      srcCd = "test"
      //쿼리스트링 / Controller에서 쿼리스트링 다음페이지로 넘기는방법
      location.href= "//localhost:8888/watch?"+srcCd
      //location.href= "https://www.naver.com"
    });


  }

  
</script>



<body>
  
  <div class="home" id="home">
    
    <video 
      src="/mp4/spyfamily.mp4"
      poster="http://ottnews.kr/Files/173/News/202205/3333_20220506143031148_c.JPG"   
      autoplay   
      muted
      >
    </video>

    <div class="overlay">
      <div class="header">
        <div class="logo">NETFLIX</div>
        <div class="nav">
          <div class="nav-item">홈</div>
          <div class="nav-item">TV 프로그램</div>
          <div class="nav-item">영화</div>
          <div class="nav-item">NEW! 요즘 대세 영화</div>
          <div class="nav-item">내가 찜한 콘텐츠</div>
        </div>
        <div class="menu">
          <div class="menu-item"><i class="fa-solid fa-magnifying-glass"></i></div>
          <div class="menu-item">키즈</div>
          <div class="menu-item"><i class="fa-solid fa-gift"></i></div>
          <div class="menu-item"><i class="fa-regular fa-bell"></i></div>
          <div class="menu-item">A</div>
        </div>
      </div>
      <div class="banner">
        <div class="series">N 시리즈</div>
        <div class="title">SpyFamily</div>
        <div class="badge">오늘 한국에서 콘텐츠 순위 1위</div>
        <div class="discription">
          스파이, 암살자 그리고 초능력자. 각자 다른 사정이 있는 세 사람이 서로에게 정체를 숨기고 가상 가족을 결성한다.
        </div>
        <div class="buttons">
          <div class="white-button">
            <i id="btnMovePlay" class="fa-solid fa-play">재생</i>
            <input type="hidden" id="mainVideo" value="">
          </div>
          <div class="grey-button">
            <i class="fa-solid fa-circle-info"></i>
            <a class="info" href="#ex1" rel="modal:open">상세정보</a>            
          </div>
         
        </div>
        <div class="extra">
          <div class="replay-button">
            <i class="fa-solid fa-arrow-rotate-left"></i>
          </div>
          <div class="rating">
            <img src="https://i.namu.wiki/i/4rqHeSQ7TkE85vF3Vlnz59QUtkq5cE095mHuoGagn8GC1uAic4hrujblFA6fJU1zbqNKuu_5AVw01CRnVQsXMQ.svg" alt="">
          </div>
        </div>
      </div>
    </div>


    <div class="category-list" id="category-list">
      <div class="category" id="category">
        <div class="title">지금뜨는컨텐츠</div>
        <div class="list">
          <div id="first" class="movie">
            <div><img src="https://source.unsplash.com/224x126/?random" alt=""></div>
            <div >asdasdiajsldkjs</div>
            <div >ddddddddjjj</div>
            <div >ddddddddjjj</div>
            <div >ddddddddjjj</div>
            
          </div>
          <div class="movie">
            <img src="https://source.unsplash.com/224x126/?random" alt="">
          </div>
          <div class="movie">
            <img src="https://source.unsplash.com/224x126/?random" alt="">
          </div>
          <div class="movie">
            <img src="https://source.unsplash.com/224x126/?random" alt="">
          </div>
          <div class="movie">
            <img src="https://source.unsplash.com/224x126/?random" alt="">
          </div>
          <div class="movie">
            <img src="https://source.unsplash.com/224x126/?random" alt="">
          </div>            
        </div>

        <div class="title">시청중인컨텐츠</div>
        <div class="list">
          <div class="movie">
            <img src="https://source.unsplash.com/224x126/?random" alt="">
          </div>
          <div class="movie">
            <img src="https://source.unsplash.com/224x126/?random" alt="">
          </div>
          <div class="movie">
            <img src="https://source.unsplash.com/224x126/?random" alt="">
          </div>
          <div class="movie">
            <img src="https://source.unsplash.com/224x126/?random" alt="">
          </div>
          <div class="movie">
            <img src="https://source.unsplash.com/224x126/?random" alt="">
          </div>
          <div class="movie">
            <img src="https://source.unsplash.com/224x126/?random" alt="">
          </div>            
        </div>

        <div class="title">찜한컨텐츠</div>
        <div class="list">
          <div class="movie">
            <img src="https://source.unsplash.com/224x126/?random" alt="">
          </div>
          <div class="movie">
            <img src="https://source.unsplash.com/224x126/?random" alt="">
          </div>
          <div class="movie">
            <img src="https://source.unsplash.com/224x126/?random" alt="">
          </div>
          <div class="movie">
            <img src="https://source.unsplash.com/224x126/?random" alt="">
          </div>
          <div class="movie">
            <img src="https://source.unsplash.com/224x126/?random" alt="">
          </div>
          <div class="movie">
            <img src="https://source.unsplash.com/224x126/?random" alt="">
          </div>            
        </div>
      </div>  
    </div>

  <div id="ex1" class="modal">
    <video
      src="/mp4/spyfamily.mp4"
      poster="http://ottnews.kr/Files/173/News/202205/3333_20220506143031148_c.JPG"   
      autoplay   
      muted
      >
    </video>
    <div class="buttons">
          <div class="white-button">
            <i id="btnMovePlay" class="fa-solid fa-play">재생</i>
            <input type="hidden" id="mainVideo" value="">
          </div>
    </div>

    <div class="detail-container">
      <div class="detail-left">
        <div class="probability">일치</div>
        <div class="detail-rating">관람등급</div>
        <div class="content">회차줄거리</div>
      </div>
  
      <div class="detail-right">
        <div class="actor">출연</div>
        <div class="genre">장르</div>
        <div class="feature">특징</div>
      </div>
    </div>

    <div class="content-list">
      <div class="content-num">1</div>
      <div class="content-num">2</div>
      <div class="content-num">3</div>
      <div class="content-num">4</div>
    </div>

    <div class="content-detail">
      여러가지 쭉 넣으세요
      출연, 장르, 특징, 관람등급, 출시일 등등
    </div>  
  
  </div>



  </div>

</body>
</html>