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
            height: 100vh;
            background: linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5)),
                url(/images/beforeLoginBack.jpg);
            background-size: cover;
            background-position: center;
            padding: 10px;
            position: relative;
            color: #fff;
        }

        .login-container{
            background: linear-gradient(rgba(0, 0, 0, 0.5))
        }
       
    </style>
</head>
<body>

<div class="main">
   <div id=login-container class="bg-dark position-absolute top-50 start-50 translate-middle" style="width: 450px; height: 600px;">
   로그인화면
   
   </div>
   
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>