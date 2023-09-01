<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<%-- css링크연결 --%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="/css/header.css">
<%-- script링크연결 --%>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<%-- 아이콘--%>
<script src="https://kit.fontawesome.com/cc89ddadc0.js" crossorigin="anonymous"></script>
</head>
<body>
    <header>
        <nav class="navbar navbar-expand">
            <div class="container-fluid">
                <%-- Logo --%>
                <a class="navbar-brand" href="/"><img class="logo-img" src="netflixhomelogo.png" alt="로고" class="d-inline-block align-top"></a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/">홈</a>
                    </li>
                    <li class="nav-item">
                    <a class="nav-link" href="#!">시리즈</a>
                    </li>
                    <li class="nav-item">
                    <a class="nav-link" href="#!">영화</a>
                    </li>
                    <li class="nav-item">
                    <a class="nav-link" href="#!" tabindex="-1" aria-disabled="true">내가 찜한 콘텐츠</a>
                    </li>
                </ul> 
                
                <%--반응형 드롭다운 메뉴--%>
                <div class="dropdown-main">
                    <a class="btn dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false">메뉴</a>
                    <ul class="dropdown-menu dropdown-main-menu" aria-labelledby="dropdownMenuLink">
                        <li><a class="dropdown-main-item" href="#">홈</a></li>
                        <li><a class="dropdown-main-item" href="#">시리즈</a></li>
                        <li><a class="dropdown-main-item" href="#">영화</a></li>
                        <li><a class="dropdown-main-item" href="#">내가 찜한 콘텐츠</a></li>
                    </ul>
                </div>


                <div class="right-menu">
                    <div class = "menu">
                    <div class="search">
                        <div class="collapse multi-collapse" id="multiCollapseExample1">
                            <div class="row search-box-inner">
                                <div class="col">
                                <input class="form-control me-2 card card-body" type="search" placeholder="Search" aria-label="Search">
                                </div>
                            </div>
                        </div>
                    </div>

                        <div class="menu-item" data-bs-toggle="collapse" href="#multiCollapseExample1" role="button" aria-expanded="false" aria-controls="multiCollapseExample1">
                            <i class="fa-solid fa-magnifying-glass"></i>
                        </div>



                        <div class="menu-item"><i class="fas fa-bell"></i></div>
                    </div>

                    <div class="dropdown">
                        <button class="btn btn-secondary dropdown-toggle custom-btn" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false" style="background: none; border: none;">
                            <img src="\Netflix-avatar1.png" alt="Dropdown Image">
                        </button>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuButton1">
                            <li><a class="dropdown-item profile-item" href="#">
                                <img src="\Netflix-avatar1.png" alt="Dropdown Image">
                                <span class="profile-name">Profile 1</span>
                            </a></li>
                            <li><a class="dropdown-item profile-item" href="#">
                                <img src="\Netflix-avatar1.png" alt="Dropdown Image">
                                <span class="profile-name">Profile 2</span>
                            </a></li>
                            <li><a class="dropdown-item profile-item" href="#">
                                <img src="\Netflix-avatar1.png" alt="Dropdown Image">
                                <span class="profile-name">Profile 3</span>
                            </a></li>
                            <li><a class="dropdown-item profile-item" href="#">
                                <img src="\Netflix-avatar1.png" alt="Dropdown Image">
                                <span class="profile-name">Profile 4</span>
                            </a></li>
                        <hr>
                            <li><a class="dropdown-item" href="#">프로필 관리</a></li>
                        <hr>
                            <li><a class="dropdown-item" href="#">계정</a></li>
                            <li><a class="dropdown-item" href="#">고객 센터</a></li>
                            <li><a class="dropdown-item" href="#">Netflix에서 로그아웃</a></li>


                        </ul>
                        </div>
                    </div>
</div>


    </div>
  </div>
            </div>
        </div>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.5.2/dist/js/bootstrap.bundle.min.js"></script>
        </nav>

</body>
</html>
