<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>원건보다하늘</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="/css/login-style.css" rel="stylesheet">
</head>
<body>

<div class="main">
   <div id=login-container class="position-absolute top-50 start-50 translate-middle d-flex justify-content-center">
    <div class="w-70 align-self-center d-grid gap-2"> 
        <div>
            <p class="fs-2 m-2">로그인</p>
        </div>
        
        <form>
            <div class="form-floating mb-3">
                <input type="email" class="form-control" id="floatingInput" placeholder="이메일주소 또는 전화번호">
                <label for="floatingInput" style="color:black;">이메일주소 또는 전화번호</label>
            </div>
            <div class="form-floating">
                <input type="password" class="form-control" id="floatingPassword" placeholder="비밀번호">
                <label for="floatingPassword" style="color:black;"> 비밀번호</label>
            </div>
            <div class="col-auto1 pt-4 align-self-center d-grid">
                <button id="login-button" type="submit" class="btn btn-danger btn-lg ">
                    <strong>로그인</strong>
                </button>
            </div>
        <div>
            <div>
                <p class="fs-5 mt-4">소셜 로그인</p>
            </div>

            <div class="m-3"> 소셜로그인 버튼</div>
        </div>    

            <div class="form-check d-flex justify-content-between mt-7">
            <div><input class="form-check-inpu" type="checkbox" value="" id="flexCheckDefault">
            <label class="form-check-label" for="flexCheckDefault" style="color:dimgray; font-size: small;">
                <strong> 로그인 정보 저장 </strong>
            </label></div>
            <div><a href="#" class="text-decoration-none" style="color:dimgray; font-size: small;"> <strong>도움이 필요하신가요?</strong></a></div>
            </div>

            <div class="d-flex justify-content-center mt-5 ">
            <div class="me-2"> <p >netflix 회원이 아닌가요?</p> </div>
            <div> <a href="/" style="color:white;"> <strong>지금 가입하세요</strong></a> </div>
            </div>
        </form>

    <div>    
    
   </div>
   
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>