<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>원건보다하늘</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <style>
        /* Your custom CSS styles here */
        .main {
            width: 100%;
            height: 600px;
            background: linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5)),
                url(/images/beforeLoginBack.jpg);
            background-size: cover;
            background-position: center;
            padding: 10px;
            position: relative;
            color: #fff;
        }

        .content-introduction {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            text-align: center;
            max-width: 80%;
            margin-top: 50px;
        }

        .content-introduction h3{
            margin-top: 1.7rem;
            margin-bottom: 1.7rem;
        }

        .row {
            display: flex;
            flex-wrap: nowrap;
        }

        .row>* {
            width: auto;
            padding-left: calc(var(--bs-gutter-x) * .5);
            margin-top: var(--bs-gutter-y);
        }
        
        .col-auto {
            width: 70%;
        }

        .main-bnr{
            flex-direction: column;
            background: radial-gradient( 51.39% 511.66% at 47.68% -217.91%, #ff9900 0%, #e50914 17.27%, #0e1b4f 79.44%, #000413 100% );
            box-shadow: 0px -8px 25px rgba(0, 0, 0, 0.5);
            width: 100%;
            height: 20vh;
        }

    </style>
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
                <input type="email" class="form-control form-control-lg " id="inputEmail" placeholder=" 이메일 주소" style="background-color: rgba(0, 0, 0, 0.5);">
            </div>
            <div class="col-auto1">
                <button type="submit" class="btn btn-danger btn-lg" style="background-color: rgba(255, 0, 0, 0.7);"> 시작하기 &gt; </button>
            </div>
        </form>
    </div>
</div>


<div class="main-bnr">
    <div class="promotion-bnr fs-6">
        <img src="/images/popcorn.png" class="rounded float-start mw-50">
        <div class="text-content text-white">
            <h3 >13500원이면 만날 수 있는 넷플릭스.</h3>
            <p>스탠다드 멤버십에 가입하세요</p>
            <a href="#">자세히 알아보기 &gt; </a>
        </div>
    </div>
</div>

<div class="netflix-introduction">
    <!-- Add your Netflix introduction content here -->
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>