<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>원건보다하늘</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="/css/index-style.css" rel="stylesheet">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

</head>
<body>

<div class="main">
    <div class="content-introduction">
        <h1><strong>영화, 시리즈등을 무제한으로</strong></h1>
        <h3>어디서나 자유롭게 시청하세요. 해지는 언제든 가능합니다.</h3>
        <p>시청할 준비가 되셨나요? 멤버십을 등록하거나 재시작하려면 이메일 주소를 입력하세요.</p>
  
        <form id="email-signup" class="row g-3 align-items-center">
            <div class="col-auto">
                <label for="inputEmail" class="visually-hidden">Email</label>
                <input type="email" class="form-control form-control-lg text-white" id="inputEmail" placeholder=" 이메일 주소" style="background-color: rgba(0, 0, 0, 0.5);">
            </div>
            <div class="col-auto1">
                <button type="submit" class="btn btn-danger btn-lg" style="background-color: rgba(255, 0, 0);"> 시작하기 &gt; </button>
            </div>
        </form>
    </div>
</div>


<div class="main-bnr">
    <div class="inner-wrap">
        <div class="inr-box promotion-bnr fs-6">
            <div class="img"><img src="/images/popcorn.png" class="bnr-loginImg"></div>
            <div class="text-content text-white">
                <h3 >13500원이면 만날 수 있는 넷플릭스.</h3>
                <p>스탠다드 멤버십에 가입하세요</p>
                <a href="#">자세히 알아보기 &gt;</a>
            </div>
        </div>
    </div>
</div>

<div class="netflix-introduction1">
   <div class="netflix-introduction-box d-flex justify-content-center">
   <div class="netflix-introduction-content">
   <h1><strong>TV로 즐기세요</strong></h1>
   <h3>스마트 TV, PlayStation, Xbox, Chromecast, Apple TV, 블루레이 플레이어등 다양한 디바이스에서 시청하세요.</h3>
   </div>
   <div class="img-fluid"><img src="/images/index-tv.png"></div>
   </div>
</div>

<div class="netflix-introduction2">
   <div  class=" d-flex justify-content-center">
   <div class="img"><img src="/images/index-introduction.png"></div>
   <div>글</div>
   </div>
</div>

<div class="netflix-introduction3">
   <div  class=" d-flex justify-content-center">
   <div class="img"><img src="/images/index-mobile.jpg"></div>
   <div>글</div>
   </div>
</div>

<div class="netflix-qna" >
   <div class="text-center md-2">
   <h1><strong>자주묻는 질문</strong></h1>
    <div class="align-self-center wrap ">
        <div class="panel-group p-3" id="accParent" role="tablist">
            <!-- 첫번째 아이템 -->
            <div class="panel panel-default border border-dark">
            <div class="panel-heading" role="tab"  style="background-color: #646464; font-size:2.5rem; text-align:left; border border-dark">
                <a role="button" class="text-decoration-none" data-toggle="collapse" data-parent="#accParent" href="#accEx1" aria-constrols="addEx1" style="color:white; margin:5px;">
                첫번째. 여기를 클릭해 주세요.
                </a>
            </div>
            <div id="accEx1" class="panel-collapse collapse" role="tabpanel">
                <div class="panel-body"  style="background-color: #505050; color:white; font-size:2rem; text-align:left;">
                href="#accEx1" 예시 입니다.
                </div>
            </div>
            </div>

            <!-- 두번째 아이템 -->
            <div class="panel panel-default border border-dark">
            <div class="panel-heading " role="tab"  style="background-color: #646464; font-size:2.5rem; text-align:left; border border-dark">
                <a role="button" data-toggle="collapse" data-parent="#accParent" data-target="#accEx2" aria-constrols="addEx2" style="color:white; margin:5px;">
                두번째. 여기를 클릭해 주세요.
                </a>
            </div>
            <div id="accEx2" class="panel-collapse collapse" role="tabpanel">
                <div class="panel-body" style="background-color: #505050; color:white; font-size:2rem; text-align:left;">
                data-target="#accEx2" 예시입니다.
                </div>
            </div>
        </div>
    </div> <!-- id=accParent -->

    <div class="text-center">
        <h4>시청할 준비가 되셨나요? 멤버십을 등록하거나 재시작하려면 이메일 주소를 입력하세요.</h4>
        <div id="index-bottom-signup">
            <form class="row g-3 align-items-center justify-content-center w-50">
                <div class="col-auto w-50">
                    <label for="inputEmail" class="visually-hidden">Email</label>
                    <input type="email" class="form-control form-control-lg text-white" id="inputEmail" placeholder=" 이메일 주소" style="background-color: rgba(0, 0, 0, 0.5);">
                </div>
                <div class="col-auto1">
                    <button type="submit" class="btn btn-danger btn-lg" style="background-color: rgba(255, 0, 0);"> 시작하기 &gt; </button>
                </div>
            </form>
        </div>
    </div> 
</div>

<footer class="content-footer footer bg-footer-theme text-center"><h1>푸터영역</h1></footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>