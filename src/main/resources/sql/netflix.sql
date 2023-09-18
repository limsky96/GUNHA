connect netflix/netflix;

drop table NETFLIX_MEMBER cascade constraints;

desc netflix_member;
select * from netflix_member;
create table NETFLIX_MEMBER(
    member_id varchar2(60) primary key,
    member_email varchar2(50),
    member_password varchar2(60) not null,
    member_card_number char(19),
    member_membership_no number not null,
    member_social varchar2(10) default 'NONE'
);


insert into netflix_member values(
    'admin',
    'admin@example.com',
    '$2a$12$AzAyvtyWTAzbQU0Cy33SvutzLTLfx1bK7FkijZ9blOJ1nQ6S690qa',
    '1234-1234-1234-1234',
    3,
    'none'
);

insert into netflix_member values(
    'tatelulove4@naver.com',
    'tatelulove4@naver.com',
    '$2a$10$cZ.aG4niQaxb.HkBgXLrVecmCDeWmQftbfHLjjTzoq1N8aLfoPQE6',
    '1234-5678-1234-5678',
    3,
    'none'
);
insert into netflix_member values(
    'tatelulove4@naver.com_kakao',
    'tatelulove4@naver.com',
    '$2a$10$7DxPpaKDw/wcVUT0//J.Aen1gkroamLzBhZOMl2YLy8GUIIdZvVTy',
    null,
    1,
    'kakao'
);

insert into netflix_member values(
    'user@example.com',
    'user@example.com',
    '$2a$10$CZq2r6Srj6U5u9sxrhX2suWRETivRLWpWRfT7KwG/glQ0kLl4k8HO',
    '1234567890123456',
    1,
    'none'
);

insert into netflix_member values(
    'tatelulove4@naver.com_naver',
    'tatelulove4@naver.com',
    '$2a$10$CZq2r6Srj6U5u9sxrhX2suWRETivRLWpWRfT7KwG/glQ0kLl4k8HO',
    '1234567890123456',
    1,
    'naver'
);

----------------------------------------------------------

drop table NETFLIX_AUTH cascade constraints;

create table NETFLIX_AUTH(
    auth_member_id varchar2(60) not null,
    auth_member_authority varchar2(10) not null,

    constraint ix_netflix_auth UNIQUE(auth_member_id, auth_member_authority),
    constraint fk_auth_member_id foreign key(auth_member_id) references NETFLIX_MEMBER(member_id)
      on delete cascade
);



insert into netflix_auth values(
    'tatelulove4@naver.com',
    'ROLE_USER'
);

insert into netflix_auth values(
    'tatelulove4@naver.com',
    'ROLE_ADMIN'
);

insert into netflix_auth values(
    'admin',
    'ROLE_ADMIN'
);

commit;

insert into netflix_auth values(
    'tatelulove4@naver.com_kakao',
    'ROLE_USER'
);

insert into netflix_auth values(
    'user@example.com',
    'ROLE_USER'
);


insert into netflix_auth values(
    'tatelulove4@naver.com_naver',
    'ROLE_USER'
);


---------------------------------------------------------

drop table NETFLIX_MEMBER_PROFILE cascade constraints;

create table NETFLIX_MEMBER_PROFILE(
    member_profile_member_id varchar2(60) not null,
    member_profile_name varchar2(50) not null,
    
    constraint fk_member_profile_member_id
        foreign key (member_profile_member_id) references NETFLIX_MEMBER(member_id)
        on delete cascade,
    constraint ix_netflix_member_profile UNIQUE(member_profile_member_id, member_profile_name)
);

alter table NETFLIX_MEMBER_PROFILE
add constraint ix_netflix_member_profile UNIQUE(member_profile_member_id, member_profile_name);


insert into NETFLIX_MEMBER_PROFILE values(
    'tatelulove4@naver.com',
    'ADMIN'
);

insert into NETFLIX_MEMBER_PROFILE values(
    'tatelulove4@naver.com_kakao',
    '테스트2'
);

insert into NETFLIX_MEMBER_PROFILE values(
    'admin',
    'ADMIN'
);


commit;

--------------------------------------------------------

drop table NETFLIX_ORDER cascade constraints;

create table NETFLIX_ORDER(
    order_id number primary key,
    order_member_id varchar2(60) not null,
    order_member_card_number char(19),
    order_start_date date default sysdate,
    order_valid char(1) default 'T', -- V(valid), C(canceled), E(expired), T(temporary)
    order_customer_uid varchar2(80) not null,
    constraint fk_order_member_id
        foreign key (order_member_id) references NETFLIX_MEMBER(member_id)
        on delete cascade
);

-- alter table netflix_order add order_customer_uid varchar2(80);

-- update netflix_order set order_customer_uid = 'tatelulove4@naver.com_kakao_cuid_order_000001' where order_member_id ='tatelulove4@naver.com_kakao';

-- insert into netflix_order values((select nvl(max(order_id),0)+1 from netflix_order), 'tatelulove4@naver.com_kakao', '1234-5678-1234-5678', '2023-09-05', 'V');

select * from netflix_order;
-- select count(*)+1 from netflix_order;
-- select count(*)+1 from netflix_order where order_member_id = 'tatelulove4@naver.com_kakao';
-- select count(*)+1 from netflix_order where order_member_id = 'tatelulove4@naver.com';
commit;
-- delete from netflix_order where order_id = 2;
-- member_id + order_id -> customer_uid -> ex) tatelulove4@naver.com_kakao_cuid_order_000001
-- projectname + order_id -> merchant_uid -> ex) project_netflix_muid_order_000001

-----------------------------------------------

drop table NETFLIX_MEMBERSHIP cascade constraints;

create table NETFLIX_MEMBERSHIP(
    membership_no number primary key,
    membership_grade varchar2(10) unique,
    membership_amount number not null
);

select * from netflix_membership;
select * from netflix_member;

select * from netflix_order;
select * from netflix_membership ms, netflix_member m, netflix_order o
    where o.order_id = 27 and o.order_member_id = m.member_id and m.member_membership_no = ms.membership_no;
-- update netflix_membership set membership_amount = 0 where membership_no = 0;
commit;
insert into netflix_membership values(
    0,
    'none',
    0
);
insert into netflix_membership values(
    1,
    'basic',
    100
);

insert into netflix_membership values(
    2,
    'standard',
    150
);
insert into netflix_membership values(
    3,
    'premium',
    200
);


alter table NETFLIX_MEMBER
add constraint fk_member_membership_no
foreign key (member_membership_no) references NETFLIX_MEMBERSHIP(membership_no)
on delete cascade;


-----------------------------------------------

drop table NETFLIX_MOVIE_DISTRIBUTION cascade constraints;

create table NETFLIX_MOVIE_DISTRIBUTION(
    movie_distribution_date date default sysdate
);

-----------------------------------------------

drop table NETFLIX_MOVIE cascade constraints;
select movie_release_date from netflix_movie;
create table NETFLIX_MOVIE(
    movie_id Number primary key, -- 영상 번호
    movie_name varchar2(200), -- 영화 이름 
    movie_postUrl varchar2(500), -- 포스터 url
    movie_trailerUrl varchar2(2000), -- 트레일러 url
    movie_content varchar2(4000), -- 줄거리 
    movie_genre varchar2(50), -- 장르 
    movie_cast varchar2(500), -- 출연진 
    movie_release_date Date, -- 개봉일 
    movie_favorite char(1), -- 찜한 컨텐츠 ?
    movie_autoplay char(1)
);
commit;
insert into NETFLIX_MOVIE values(
    1,
    '어바웃타임',
    'https://t1.daumcdn.net/cfile/tistory/99EDC1475B77987606',
    'https://www.youtube.com/embed/T7A810duHvw?mute=1&autoplay=1&controls=0',
    '모태솔로 팀(돔놀 글리슨)은 성인이 된 날, 아버지(빌 나이)로부터 놀랄만한 가문의 비밀을 듣게 된다. 바로 시간을 되돌릴 수 있는 능력이 있다는 것! 그것이 비록 히틀러를 죽이거나 여신과 뜨거운 사랑을 할 수는 없지만, 여자친구는 만들어 줄 순 있으리.. 꿈을 위해 런던으로 간 팀은 우연히 만난 사랑스러운 여인 메리에게 첫눈에 반하게 된다. 그녀의 사랑을 얻기 위해 자신의 특별한 능력을 마음껏 발휘하는 팀. 어설픈 대시, 어색한 웃음은 리와인드! 뜨거웠던 밤은 더욱 뜨겁게 리플레이! 꿈에 그리던 그녀와 매일매일 최고의 순간을 보낸다. 하지만 그와 그녀의 사랑이 완벽해질수록 팀을 둘러싼 주변 상황들은 미묘하게 엇갈리고, 예상치 못한 사건들이 여기저기 나타나기 시작하는데… 어떠한 순간을 다시 살게 된다면, 과연 완벽한 사랑을 이룰 수 있을까?',
    '로맨스',
    '["도널 글리슨", "레이첼 맥아담스"]',
    TO_DATE('2013-12-05', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    2,
    '타이타닉',
    'https://upload.wikimedia.org/wikipedia/ko/a/a0/%ED%83%80%EC%9D%B4%ED%83%80%EB%8B%89.jpg',
    'https://www.youtube.com/embed/I7c1etV7D7g?mute=1&autoplay=1&controls=0',
    '내 인생의 가장 큰 행운은 당신을 만난 거야" 우연한 기회로 티켓을 구해 타이타닉호에 올라탄 자유로운 영혼을 가진 화가 ‘잭’(레오나르도 디카프리오)은 막강한 재력의 약혼자와 함께 1등실에 승선한 ‘로즈’(케이트 윈슬렛)에게 한눈에 반한다. 진실한 사랑을 꿈꾸던 ‘로즈’ 또한 생애 처음 황홀한 감정에 휩싸이고, 둘은 운명 같은 사랑에 빠지는데… 가장 차가운 곳에서 피어난 뜨거운 사랑! 영원히 가라앉지 않는 세기의 사랑이 펼쳐진다!',
    '로맨스',
    '["레오나르도 디카프리오", "케이트 윈슬렛"]',
    TO_DATE('1998-02-20', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    3,
    '비포선라이즈',
    'https://www.themoviedb.org/t/p/original/fbVO1qBOt3jiWQTJpwHvSMUT0lf.jpg',
    'https://www.youtube.com/embed/iljUuBESmfg?mute=1&autoplay=1&controls=0',
    '파리로 돌아가는 셀린과 비엔나로 향하는 제시. 기차 안에서 우연히 만난 그들은 짧은 시간에 서로에게 빠져든다. “나와 함께 비엔나에 내려요” 그림 같은 도시와 꿈같은 대화 속에서 발견한 서로를 향한 강한 이끌림은 풋풋한 사랑으로 물들어 간다. 밤새도록 계속된 그들의 사랑 이야기 끝에 해가 떠오르기 시작하고 그들은 헤어져야만 하는데… 단 하루, 사랑에 빠지기 충분한 시간 낭만적인 로맨스가 다시 피어오른다.',
    '로맨스',
    '["도널 글리슨", "레이첼 맥아담스"]',
    TO_DATE('2016-04-07', 'YYYY-MM-DD'),
    'N',
    'Y');    
insert into NETFLIX_MOVIE values(
    4,
    '이프온리',
    'https://ojsfile.ohmynews.com/PHT_IMG_FILE/2016/1222/IE002072244_PHT.jpg',
    'https://www.youtube.com/embed/3n-shxX68ug?mute=1&autoplay=1&controls=0',
    '눈앞에서 사랑하는 연인을 잃은 남자는 다음 날 아침, 자신의 옆에서 자고 있는 연인을 보고 소스라치게 놀란다. 기쁨도 잠시, 정해진 운명은 바꿀 수 없단 것을 깨달은 그는 더 늦기 전에 자신의 진정한 사랑을 전하기로 마음먹는데… 네가 아니었다면 난 영영 사랑을 몰랐을 거야 사랑하는 법을 알게 해줘서 고마워, 사랑받는 법도.',
    '로맨스',
    '["제니퍼 러브휴잇", "폴 니콜스"]',
    TO_DATE('2004-10-29', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    5,
    '노트북',
    'https://ojsfile.ohmynews.com/down/images/1/betrayed_198876_1[259756].jpg',
    'https://www.youtube.com/embed/AF4IzD6aVU4?mute=1&autoplay=1&controls=0',
    '17살, ‘노아’는 밝고 순수한 ‘앨리’를 보고 첫눈에 반한다. 빠른 속도로 서로에게 빠져드는 둘. 그러나 이들 앞에 놓인 장벽에 막혀 이별하게 된다. 24살, ‘앨리’는 우연히 신문에서 ‘노아’의 소식을 접하고 잊을 수 없는 첫사랑 앞에서 다시 한 번 선택의 기로에 서게 되는데… 열일곱의 설렘, 스물넷의 아픈 기억, 그리고 마지막까지… 한 사람을 지극히 사랑했으니 내 인생은 성공한 인생입니다',
    '로맨스',
    '["라이언 고슬링", "레이첼 맥아담스"]',
    TO_DATE('2004-11-26', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    6,
    '500일의 썸머',
    'https://t1.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/G64/image/1XBxBg5M2CLuNRgp116gA9y8sms.jpg',
    'https://www.youtube.com/embed/yWuTr4-0tnk?mute=1&autoplay=1&controls=0',
    '운명적 사랑을 믿는 남자 ‘톰’ 모든 것이 특별한 여자 ‘썸머’에 완전히 빠졌다. 사랑은 환상일 뿐이라고 생각하는 여자 ‘썸머’ 친구인 듯 연인 같은 ‘톰’과의 부담 없는 썸이 즐겁다. “저기… 우리는 무슨 관계야?” 설렘으로 가득한 시간도 잠시 두 사람에게도 피할 수 없는 선택의 순간이 찾아오는데… “우리 모두의 단짠단짠 연애담!” 설레는 1일부터 씁쓸한 500일까지 서로 다른 남녀의 극사실주의 하트시그널!',
    '로맨스',
    '["조셉 고든 레빗", "주이 디샤넬"]',
    TO_DATE('2010-01-21', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    7,
    '러브 액츄얼리',
    'https://upload.wikimedia.org/wikipedia/ko/e/e0/%EB%9F%AC%EB%B8%8C%EC%95%A1%EC%B8%84%EC%96%BC%EB%A6%AC_%ED%8F%AC%EC%8A%A4%ED%84%B0.jpg',
    'https://www.youtube.com/embed/PP3g0WzGdQY?mute=1&autoplay=1&controls=0',
    '사랑에 상처받은 당신을 위해, 사랑하지만 말하지 못했던 당신을 위해, 사랑에 확신하지 못했던 당신을 위해, 모두의 마음을 따뜻하게 할 선물이 찾아옵니다.',
    '로맨스',
    '["휴 그랜트", "리암 니슨", "콜린 퍼스", "로라 리니", "엠마 톰슨", "앨런 릭먼", "키이라 니이틀리", "마틴 맥커친", "빌 나이"]',
    TO_DATE('2003-12-05', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    8,
    '라라랜드',
    'https://i.pinimg.com/550x/d8/73/c5/d873c5098705172190f2fdbc7e4abe72.jpg',
    'https://www.youtube.com/embed/0pdqf4P9MB8?mute=1&autoplay=1&controls=0',
    '꿈을 꾸는 사람들을 위한 별들의 도시 ‘라라랜드’. 재즈 피아니스트 ‘세바스찬’(라이언 고슬링)과 배우 지망생 ‘미아’(엠마 스톤), 인생에서 가장 빛나는 순간 만난 두 사람은 미완성인 서로의 무대를 만들어가기 시작한다.',
    '로맨스',
    '["라이언 고슬링", "엠마 스톤"]',
    TO_DATE('2016-12-07', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    9,
    '너의 이름은',
    'https://upload.wikimedia.org/wikipedia/ko/d/d1/%EB%84%88%EC%9D%98_%EC%9D%B4%EB%A6%84%EC%9D%80.jpg',
    'https://www.youtube.com/embed/enRm-9qF2L8?mute=1&autoplay=1&controls=0',
    '아직 만난 적 없는 너를, 찾고 있어 천년 만에 다가오는 혜성 기적이 시작된다 도쿄에 사는 소년 ‘타키’와 시골에 사는 소녀 ‘미츠하’는 서로의 몸이 뒤바뀌는 신기한 꿈을 꾼다 낯선 가족, 낯선 친구들, 낯선 풍경들... 반복되는 꿈과 흘러가는 시간 속, 마침내 깨닫는다 우리, 서로 뒤바뀐 거야? 절대 만날 리 없는 두 사람 반드시 만나야 하는 운명이 되다 서로에게 남긴 메모를 확인하며 점점 친구가 되어가는 ‘타키’와 ‘미츠하’ 언제부턴가 더 이상 몸이 바뀌지 않자 자신들이 특별하게 이어져있었음을 깨달은 ‘타키’는 ‘미츠하’를 만나러 가는데... 잊고 싶지 않은 사람 잊으면 안 되는 사람 너의 이름은?',
    '로맨스',
    '["카미키 류노스케", "카미시라이시 모네"]',
    TO_DATE('2017-01-04', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    10,
    '비긴 어게인',
    'https://www.themoviedb.org/t/p/original/xABk5PBEPYD6YxLdWGZjMjAktR2.jpg',
    'https://www.youtube.com/embed/rbf5TPL81ag?mute=1&autoplay=1&controls=0',
    '싱어송라이터인 ‘그레타’(키이라 나이틀리)는 남자친구 ‘데이브’(애덤 리바인)가 메이저 음반회사와 계약을 하게 되면서 뉴욕으로 오게 된다. 그러나 행복도 잠시, 오랜 연인이자 음악적 파트너로서 함께 노래를 만들고 부르는 것이 좋았던 그레타와 달리 스타가 된 데이브의 마음은 어느새 변해버린다. 스타 음반프로듀서였지만 이제는 해고된 ‘댄’(마크 러팔로)은 미치기 일보직전 들른 뮤직바에서 그레타의 자작곡을 듣게 되고 아직 녹슬지 않은 촉을 살려 음반제작을 제안한다. 거리 밴드를 결성한 그들은 뉴욕의 거리를 스튜디오 삼아 진짜로 부르고 싶었던 노래를 만들어가는데…',
    '로맨스',
    '["키이라 나이틀리", "마크 러팔로", "애덤 리바인", "헤일리 스테인펠드"]',
    TO_DATE('2014-08-13', 'YYYY-MM-DD'),
    'N',
    'Y');
    
insert into NETFLIX_MOVIE values(
    11,
    '엑시트',
    'https://i.namu.wiki/i/7K9dzEVD5c2R5hl1kNjyMqXeaYpteSl3OKkIudRLzM9A7lPRsYYh3xAHDxROiyAYoWYIu6ypb_v37jreRjgF7A.webp',
    'https://www.youtube.com/embed/UXVk_04Ul3M?mute=1&autoplay=1&controls=0',
    '대학교 산악 동아리 에이스 출신이지만 졸업 후 몇 년째 취업 실패로 눈칫밥만 먹는 용남은 온 가족이 참석한 어머니의 칠순 잔치에서 연회장 직원으로 취업한 동아리 후배 의주를 만난다 어색한 재회도 잠시, 칠순 잔치가 무르익던 중 의문의 연기가 빌딩에서 피어 오르며 피할 새도 없이 순식간에 도심 전체는 유독가스로 뒤덮여 일대혼란에 휩싸이게 된다. 용남과 의주는 산악 동아리 시절 쌓아 뒀던 모든 체력과 스킬을 동원해 탈출을 향한 기지를 발휘하기 시작하는데…',
    '코미디',
    '["조정석", "윤아"]',
    TO_DATE('2019-07-31', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    12,
    '헬로우 고스트',
    'https://www.themoviedb.org/t/p/original/eABJoKMxXky1R13aR2E9It90Djm.jpg',
    'https://www.youtube.com/embed/BEi8Ck8pHUc?mute=1&autoplay=1&controls=0',
    '죽는 게 소원인 외로운 남자 상만(차태현). 어느 날 그에게 귀신이 보이기 시작한다. 거머리처럼 딱 달라붙은 변태귀신, 꼴초귀신, 울보귀신, 초딩귀신. 소원을 들어달라는 귀신과 그들 때문에 죽지도 못하게 된 상만. 결국 그들의 소원을 들어주는 사이, 예상치 못했던 생애 최고의 순간과 마주하게 되는데…',
    '코미디',
    '["차태현", "강예원", "이문수", "고창석", "장영남", "천보근"]',
    TO_DATE('2010-12-22', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    13,
    '수상한 그녀',
    'https://img.hankyung.com/photo/202104/0Q.26019307.1-1200x.jpg',
    'https://www.youtube.com/embed/7k5Zljg7EFQ?mute=1&autoplay=1&controls=0',
    '아들 자랑이 유일한 낙인 욕쟁이 칠순 할매 오말순(나문희分)은 어느 날, 가족들이 자신을 요양원으로 독립(?)시키려 한다는 청천벽력 같은 사실을 알게 된다. 뒤숭숭한 마음을 안고 밤길을 방황하던 할매 말순은 오묘한 불빛에 이끌려 ‘청춘 사진관’으로 들어간다. 난생 처음 곱게 꽃단장을 하고 영정사진을 찍고 나오는 길, 그녀는 버스 차창 밖에 비친 자신의 얼굴을 보고 경악을 금치 못한다. 오드리 헵번처럼 뽀얀 피부, 날렵한 몸매... 주름진 할매에서 탱탱한 꽃처녀의 몸으로 돌아간 것! 아무도 알아보지 못하는 자신의 젊은 모습에 그녀는 스무살 ‘오두리’가 되어 빛나는 전성기를 즐겨 보기로 마음 먹는데... 2014년 새해, 대한민국에 웃음 보따리를 안겨줄 <수상한 그녀>가 온다!',
    '코미디',
    '["심은경", "나문희", "박인환", "성동일", "이진욱"]',
    TO_DATE('2014-01-22', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    14,
    '극한직업',
    'https://web-cf-image.cjenm.com/crop/660x950/public/share/metamng/programs/extremejob-movie-poster-ko-001-01.jpg_1645638949085.jpg?v=1679468009',
    'https://www.youtube.com/embed/l9Hu3Xocc-g?mute=1&autoplay=1&controls=0',
    '불철주야 달리고 구르지만 실적은 바닥, 급기야 해체 위기를 맞는 마약반! 더 이상 물러설 곳이 없는 팀의 맏형 고반장은 국제 범죄조직의 국내 마약 밀반입 정황을 포착하고 장형사, 마형사, 영호, 재훈까지 4명의 팀원들과 함께 잠복 수사에 나선다. 마약반은 24시간 감시를 위해 범죄조직의 아지트 앞 치킨집을 인수해 위장 창업을 하게 되고, 뜻밖의 절대미각을 지닌 마형사의 숨은 재능으로 치킨집은 일약 맛집으로 입소문이 나기 시작한다. 수사는 뒷전, 치킨장사로 눈코 뜰 새 없이 바빠진 마약반에게 어느 날 절호의 기회가 찾아오는데… 범인을 잡을 것인가, 닭을 잡을 것인가!',
    '코미디',
    '["류승룡", "이하늬", "진선규", "이동휘" ,"공명"]',
    TO_DATE('2019-01-23', 'YYYY-MM-DD'),
    'N',
    'Y'); 
insert into NETFLIX_MOVIE values(
    15,
    '웜 바디스',
    'https://t1.daumcdn.net/cfile/125AD14851243BEC26',
    'https://www.youtube.com/embed/_TDYhTNpUzo?mute=1&autoplay=1&controls=0',
    '이름도, 나이도, 자신이 누구였는지 전혀 기억하지 못하는 좀비 ‘R’. 폐허가 된 공항에서 다른 좀비들과 무기력하게 살아가고 있던 ‘R’은 우연히 아름다운 소녀 ‘줄리’를 만난다. 이때부터 차갑게 식어있던 ‘R’의 심장이 다시 뛰고, 그의 삶에 놀라운 변화가 시작되는데... ‘줄리’를 헤치려는 좀비들 사이에서 그녀를 지켜내기 위해 고군분투 하는 ‘R’. 그리고 좀비를 죽이려는 인간들로부터 ‘R’을 지켜주려는 ‘줄리’. 누구도 상상하지 못했던 둘의 사랑은 전쟁터가 되어버린 세상을 바꾸기 시작한다. 사랑할 수 밖에 없는 좀비 ‘R’과 ‘줄리’의 유쾌하고 치열한 로맨스가 시작된다!',
    '코미디',
    '["니콜라스 홀트", "테레사 팔머", "존 말코비치"]',
    TO_DATE('2013-03-14', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    16,
    '세얼간이',
    'https://mblogthumb-phinf.pstatic.net/20110601_174/editoremail_1306917116992q2BRM_JPEG/3_IDIOTS_MAIN_IMAGES_1.JPG?type=w420',
    'https://www.youtube.com/embed/0wmjt0rDzNg?mute=1&autoplay=1&controls=0',
    '천재들만 간다는 일류 명문대 ICE, 성적과 취업만을 강요하는 학교를 발칵 뒤집어 놓은 대단한 녀석 란초! 아버지가 정해준 꿈, `공학자`가 되기 위해 정작 본인이 좋아하는 일은 포기하고 공부만하는 파파보이 파르한! 찢어지게 가난한 집, 병든 아버지와 식구들을 책임지기 위해 무조건 대기업에 취직해야만 하는 라주! 친구의 이름으로 뭉친 `세 얼간이`! 삐딱한 천재들의 진정한 꿈을 찾기 위한 세상 뒤집기 한판이 시작된다!',
    '코미디',
    '["아미르 칸", "마드하반", "셔먼 조쉬", "카리나 카푸르", "보만 이라니", "오미 베이디아"]',
    TO_DATE('2011-08-18', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    17,
    '7번방의 선물',
    'https://images.squarespace-cdn.com/content/v1/586ebc34d482e9c69268b69a/1546907892392-8O2KV2S3J48IKUVKFKKP/7%EB%B2%88%EB%B0%A9%EC%9D%98+%EC%84%A0%EB%AC%BC.jpg',
    'https://www.youtube.com/embed/rvcN5uFf_VI?mute=1&autoplay=1&controls=0',
    '최악의 흉악범들이 모인 교도소 7번방에 이상한 놈이 들어왔다! 그는 바로 6살 지능의 딸바보 용구! 평생 죄만 짓고 살아온 7번방 패밀리들에게 떨어진 미션은 바로 용구 딸 예승이를 외부인 절대 출입금지인 교도소에 반.입.하.는.것! 2013년 새해, 웃음과 감동 가득한 사상초유의 합동작전이 시작된다!',
    '코미디',
    '["류승룡", "박신혜", "갈소원", "오달수", "박원상", "김정태", "정만식", "김기천"]',
    TO_DATE('2013-01-23', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    18,
    '과속스캔들',
    'https://image.tving.com/upload/cms/caim/CAIM0400/M000333433.jpg/dims/resize/1280',
    'https://www.youtube.com/embed/q6q6xILDVm8?mute=1&autoplay=1&controls=0',
    '한때 아이돌 스타로 10대 소녀 팬들의 영원한 우상이었던 남현수(차태현). 지금은 서른 중반의 나이지만, 그래도 아직까지는 잘나가는 연예인이자, 청취율 1위의 인기 라디오 DJ. 어느 날 애청자를 자처하며 하루도 빠짐없이 라디오에 사연을 보내오던 황.정.남(박보영)이 느닷없이 찾아와 자신이 현수가 과속해서 낳은 딸이라며 바득바득 우겨대기 시작하는데!! 그것도 애까지 달고 나타나서…… 집은 물론 현수의 나와바리인 방송국까지. 어디든 물불 안 가리고 쫓아다니는 스토커 정남으로 인해 완벽했던 인생에 태클 한방 제대로 걸린 현수. 설상가상 안 그래도 머리 복잡한 그에게 정남과 스캔들까지 휩싸이게 되는데… 나 이제, 이거 한방 터지면 정말 끝이다! 끝!!',
    '코미디',
    '["차태현", "박보영", "왕석현"]',
    TO_DATE('2008-12-03', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    19,
    '조조 래빗',
    'https://www.themoviedb.org/t/p/original/zyKOqb4K8UVBnhvKJ3qiqF2l8jq.jpg',
    'https://www.youtube.com/embed/po3Gz3-lFmc?mute=1&autoplay=1&controls=0',
    '제2차 세계대전 말기, 엄마 ‘로지’(스칼렛 요한슨)와 단둘이 살고 있는 10살 소년 ‘조조’(로만 그리핀 데이비스). 원하던 독일 소년단에 입단하지만 겁쟁이 토끼라 놀림 받을 뿐이다. 상심한 ‘조조’에게 상상 속 친구 ‘히틀러’(타이카 와이티티)는 유일한 위안이 된다. ‘조조’는 어느 날 우연히 집에 몰래 숨어 있던 미스터리한 소녀 ‘엘사’(토마신 맥켄지)를 발견하게 된다. 세상에서 가장 위험한 인물이 왜 여기에?! 당신을 웃긴 만큼 따뜻하게 안아줄 이야기가 펼쳐진다!',
    '코미디',
    '["스칼릿 조핸슨", "로만 그리핀 데이비스", "타이카 와이티티", "토마신 맥켄지"]',
    TO_DATE('2020-02-05', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    20,
    '위험한 패밀리',
    'https://t1.daumcdn.net/cfile/tistory/2566143456F4A41914',
    'https://www.youtube.com/embed/EF2s4T9cI30?mute=1&autoplay=1&controls=0',
    '범죄조직 보스 프레드(로버트 드 니로)가 그의 조직을 밀고했다! 막강한 권력 버리고, 조직원에 쫓기는 신세가 된 전직 보스와 그의 가족들! 퇴물 CIA요원 스탠스필드(토미 리 존스)는 증인보호 자격으로 이들 가족을 프랑스 작은 시골마을로 보내는데.. 조용해도 너무 조용한 시골마을! 잠재울 수 없는 액션 본능이, 전직 보스 가족을 자극한다! 위험한 패밀리에 의해 초토화가 될 위기에 빠진 평화로운 마을! 과연, 위험한 패밀리는 액션 본능을 잠재우고 무사히 지낼 수 있을까?',
    '코미디',
    '["로버트 드 니로", "미셸 파이퍼", "토미 리 존스"]',
    TO_DATE('2014-01-22', 'YYYY-MM-DD'),
    'N',
    'Y');
    
insert into NETFLIX_MOVIE values(
    21,
    '해리 포터와 마법사의 돌',
    'https://www.themoviedb.org/t/p/original/kOmSNeF3yFtIgATyV544C2w8yM8.jpg',
    'https://www.youtube.com/embed/dlSkyNLOncY?mute=1&autoplay=1&controls=0',
    '해리 포터(다니엘 래드클리프 분)는 위압적인 버논 숙부(리챠드 그리피스 분)와 냉담한 이모 페투니아 (피오나 쇼 분), 욕심 많고 버릇없는 사촌 더즐리(해리 멜링 분) 밑에서 갖은 구박을 견디며 계단 밑 벽장에서 생활한다. 이모네 식구들 역시 해리와의 동거가 불편하기는 마찬가지. 이모 페투니아에겐 해리가 이상한(?) 언니 부부에 관한 기억을 떠올리게 만드는 달갑지 않은 존재다. 열 한살 생일이 며칠 앞으로 다가왔지만 한번도 생일파티를 치르거나 제대로 된 생일선물을 받아 본 적이 없는 해리로서는 특별히 신날 것도 기대 할 것도 없다. 열 한 살 생일을 며칠 앞둔 어느 날 해리에게 초록색 잉크로 쓰여진 한 통의 편지가 배달된다. 그 편지의 내용은 다름 아닌 해리의 열 한 살 생일을 맞이하여 전설적인호그와트 마법학교에서 보낸 입학초대장이었다. 그리고 해리의 생일을 축하하러 온 거인 해그리드는 해리가 모르고 있었던 해리의 진정한 정체를 알려주는데. 그것은 바로 해리가 굉장한 능력을 지닌 마법사라는 것! 해리는 해그리드의 지시대로 자신을 구박하던 이모네 집을 주저없이 떠나 호그와트행을 택한다. 런던의 킹스크로스 역에 있는 비밀의 승장장에서 호그와트 특급열차를 탄 해리는 열차 안에서 같은 호그와트 마법학교 입학생인 헤르미온느 그레인저(엠마 왓슨 분)와 론 위즐리 (루퍼트 그린트 분)를 만나 친구가 된다. 이들과 함께 호그와트에 입학한 해리는, 놀라운 모험의 세계를 경험하며 갖가지 신기한 마법들을 배워 나간다. 또한 빗자루를 타고 공중을 날아다니며 경기하는 스릴 만점의 퀴디치 게임에서 스타로 탄생하게 되며, 용, 머리가 셋 달린 개, 유니콘, 켄타우루스, 히포그리프(말 몸에 독수리 머리와 날개를 가진 괴물)등 신비한 동물들과 마주치며 모험을 즐긴다. 그러던 어느 날 해리는 호그와트 지하실에 영원한 생을 가져다주는 마법사의 돌이 비밀리에 보관되어 있다는 것을 알게되고, 해리의 부모님을 죽인 볼드모트가 그 돌을 노린다는 사실도 알게 된다. 볼드모트는 바로 해리를 죽이려다 실패하고 이마에 번개모양의 흉터를 남긴 장본인이다. 해리는 볼드모트로부터 마법의 돌과 호그와트 마법학교를 지키기 위해 필사의 노력을 하는데...',
    '판타지',
    '["다니엘 래드 클리프", "루퍼트 그린트", "엠마 왓슨"]',
    TO_DATE('2001-12-14', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    22,
    '나니아 연대기-사자, 마녀 그리고 옷장',
    'https://www.themoviedb.org/t/p/original/vSY6MgDWD5Ch5I87X8BaxZyydCI.jpg',
    'https://www.youtube.com/embed/usEkWtuNn-w?mute=1&autoplay=1&controls=0',
    '<반지의 제왕> 제작진이 선사하는 2005년 판타지 액션 대작 <나니아 연대기:사자, 마녀, 그리고 옷장>! 판타지 소설의 걸작 C. S. 루이스 원작을 바탕으로 한 <나니아 연대기:사자, 마녀, 그리고 옷장>은 총 7편의 작품 중 그 서막을 여는 첫번째 이야기이다. 2차 세계대전 중, 전쟁을 피해 먼 친척 집에 맡겨진 네 남매들은 어느날, 그 저택에 있는 마법의 옷장을 통해 환상의 나라 나니아에 들어가게 된다. 마녀의 마법에 빠져 영원히 겨울만 계속되는 나니아... 아이들은 위대한 사자 아슬란과 함께 위험에 빠진 나니아를 구하기 위해 불가능한 모험을 시작하는데.... 올 겨울, 운명이 열리는 순간 신화의 연대기가 시작된다!',
    '판타지',
    '["조지 헨리", "윌리암 모즐리", "스캔다 케인즈", "안나 팝플웰", "틸다 스윈튼", "리암 니슨"]',
    TO_DATE('2005-12-29', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    23,
    '쥬라기 월드:도미니언',
    'https://cdn.imweb.me/upload/S201706085938a6d7c7272/4bca8c4494f95.jpg',
    'https://www.youtube.com/embed/DSEfRVqjbFA?mute=1&autoplay=1&controls=0',
    '공룡들의 터전이었던 이슬라 누블라 섬이 파괴된 후, 마침내 공룡들은 섬을 벗어나 세상 밖으로 출몰한다. 지상에 함께 존재해선 안 될 위협적 생명체인 공룡의 등장으로 인류 역사상 겪어보지 못한 사상 최악의 위기를 맞이한 인간들. 지구의 최상위 포식자 자리를 걸고 인간과 공룡의 최후의 사투가 펼쳐진다.',
    '판타지',
    '["크리스 프랫", "브라이스 달라스 하워드"]',
    TO_DATE('2022-06-01', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    24,
    '신과 함께-죄와 벌',
    'https://i.namu.wiki/i/-nFZ5XeXyYtQiAtj8eutI8iTNsBJ2yrtfY404Qe8IP6wgHeutdz5f-RrqX4acFcR3fn8RUeLccSzGUkBxw1zYw.webp',
    'https://www.youtube.com/embed/5O5PVvHTWRo?mute=1&autoplay=1&controls=0',
    '저승 법에 의하면, 모든 인간은 사후 49일 동안 7번의 재판을 거쳐야만 한다. 살인, 나태, 거짓, 불의, 배신, 폭력, 천륜 7개의 지옥에서 7번의 재판을 무사히 통과한 망자만이 환생하여 새로운 삶을 시작할 수 있다. “김자홍 씨께선, 오늘 예정 대로 무사히 사망하셨습니다” 화재 사고 현장에서 여자아이를 구하고 죽음을 맞이한 소방관 자홍, 그의 앞에 저승차사 해원맥과 덕춘이 나타난다. 자신의 죽음이 아직 믿기지도 않는데 덕춘은 정의로운 망자이자 귀인이라며 그를 치켜세운다. 저승으로 가는 입구, 초군문에서 그를 기다리는 또 한 명의 차사 강림, 그는 차사들의 리더이자 앞으로 자홍이 겪어야 할 7개의 재판에서 변호를 맡아줄 변호사이기도 하다. 염라대왕에게 천년 동안 49명의 망자를 환생시키면 자신들 역시 인간으로 환생시켜 주겠다는 약속을 받은 삼차사들, 그들은 자신들이 변호하고 호위해야 하는 48번째 망자이자 19년 만에 나타난 의로운 귀인 자홍의 환생을 확신하지만, 각 지옥에서 자홍의 과거가 하나 둘씩 드러나면서 예상치 못한 고난과 맞닥뜨리는데… 누구나 가지만 아무도 본 적 없는 곳, 새로운 세계의 문이 열린다!',
    '판타지',
    '["하정우", "차태현", "주지훈", "김향기", "김동욱", "마동석"]',
    TO_DATE('2017-12-20', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    25,
    '판의 미로-오필리아와 세 개의 열쇠',
    'https://www.themoviedb.org/t/p/original/gtce25cra64QLmnbK6dnqzfRcRz.jpg',
    'https://www.youtube.com/embed/07OiMghLXao?mute=1&autoplay=1&controls=0',
    '1944년 스페인, 내전은 끝났지만 숲으로 숨은 시민군은 파시스트 정권에 계속해서 저항했고 그들을 진압하기 위해 정부군이 곳곳에 배치된다. ‘오필리아’는 만삭의 엄마 ‘카르멘’과 함께 새아버지 ‘비달’ 대위가 있는 숲속 기지로 거처를 옮긴다. 정부군 소속으로 냉정하고 무서운 비달 대위를 비롯해 모든 것이 낯설어 두려움을 느끼던 오필리아는 어느 날 숲속에서 숨겨진 미로를 발견한다. 그리고 그곳에서 자신을 “산이고 숲이자 땅”이라 소개하는 기괴한 모습의 요정 ‘판’과 만난다. 오필리아를 반갑게 맞이한 판은, 그녀가 지하 왕국의 공주 ‘모안나’이며 보름달이 뜨기 전까지 세 가지 임무를 끝내면 돌아갈 수 있다고 알려주면서 미래를 볼 수 있는 “선택의 책”을 건넨다. 오필리아는 전쟁보다 더 무서운 현실 속에서 인간 세계를 떠나 지하 왕국으로 돌아가기로 결심하게 되는데… 용기, 인내, 그리고 마지막 임무… 판의 미로가 다시 열리고, 환상과 현실의 경계가 무너진다!',
    '판타지',
    '["이바나 바쿠에로", "더그 존스"]',
    TO_DATE('2006-11-30', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    26,
    '인셉션',
    'https://newsimg.sedaily.com/2020/01/01/1YXH78H3R5_1.jpg',
    'https://www.youtube.com/embed/mucmHmjU9YE?mute=1&autoplay=1&controls=0',
    '타인의 꿈에 들어가 생각을 훔치는 특수 보안요원 코브. 그를 이용해 라이벌 기업의 정보를 빼내고자 하는 사이토는 코브에게 생각을 훔치는 것이 아닌, 생각을 심는 ‘인셉션’ 작전을 제안한다. 성공 조건으로 국제적인 수배자가 되어있는 코브의 신분을 바꿔주겠다는 거부할 수 없는 제안을 하고, 사랑하는 아이들에게 돌아가기 위해 그 제안을 받아들인다. 최강의 팀을 구성, 표적인 피셔에게 접근해서 ‘인셉션’ 작전을 실행하지만 예기치 못한 사건들과 마주하게 되는데… 꿈 VS 현실 시간, 규칙, 타이밍 모든 것이 완벽해야만 하는, 단 한 번도 성공한 적 없는 ‘인셉션’ 작전이 시작된다!',
    '판타지',
    '["레오나르도 디카프리오", "와타나베 켄", "조셉 고든 레빗", "마리옹 꼬띠아르", "엘리엇 페이지", "톰 하디"]',
    TO_DATE('2010-07-21', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    27,
    '포레스트 검프',
    'https://image.chosun.com/sitedata/image/201709/27/2017092702031_0.jpg',
    'https://www.youtube.com/embed/XHhAG-YLdk8?mute=1&autoplay=1&controls=0',
    '불편한 다리, 남들보다 조금 떨어지는 지능을 가진 외톨이 소년 ‘포레스트 검프’ 헌신적이고 강인한 어머니의 보살핌과 콩깍지 첫사랑 소녀 ‘제니’와의 만남으로 사회의 편견과 괴롭힘 속에서도 따뜻하고 순수한 마음을 지니고 성장한다. 여느 날과 같이 또래들의 괴롭힘을 피해 도망치던 포레스트는 누구보다 빠르게 달릴 수 있는 자신의 재능을 깨닫고 늘 달리는 삶을 살아간다. 포레스트의 재능을 발견한 대학에서 그를 미식축구 선수로 발탁하고, 졸업 후에도 뛰어난 신체능력으로 군에 들어가 누구도 예상치 못한 성과를 거둬 무공훈장을 수여받는 등 탄탄한 인생 가도에 오르게 된 포레스트. 하지만 영원히 행복할 것만 같았던 시간도 잠시, 어머니가 병에 걸려 죽음을 맞이하고 첫사랑 제니 역시 그의 곁을 떠나가며 다시 한번 인생의 전환점을 맞이하게 되는데… 과연, 포레스트는 진정한 삶의 행복을 발견할 수 있을까? 진정한 삶의 가치와 의미를 제시하는 감동 바이블! 올 가을, 다시 한번 세상에서 가장 눈부신 달리기가 시작된다! “Run! Forrest Run!”',
    '판타지',
    '["톰 행크스"]',
    TO_DATE('1994-10-15', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    28,
    '스타워즈 3-시스의 복수',
    'https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788900429985.jpg',
    'https://www.youtube.com/embed/opfJIo__ANQ?mute=1&autoplay=1&controls=0',
    '아주 먼 옛날 은하계 저편에...(A long time ago in a galaxy far, far away...) 전쟁! 공화국(The Republic)은 시스의 군주, 두쿠 백작(Sith Lord, Count Dooku)의 무자비한 공격에 무너지고 있었다. 양측엔 영웅들이 있었고, 악(Evil)은 어디에나 존재했다. 드로이드의 사악한 지배자 그리비어스 장군(General Grievous)은 재빠른 행보로 공화국의 수도로 침입, 은하계 의회의 의장인 팰퍼틴(Chancellor Palpatine)을 납치했다. 분리주의자 드로이드 군대가 귀중한 인질과 함께 포위된 수도의 탈출을 시도하고, 두 제다이 기사(Jedi Knights)가 의장 구출의 중대한 임무를 이끈다. 클론 전쟁이 시작되었던 때로부터 3년이 지나고 팰퍼틴 의장(황제)과 제다이 사이의 불화는 더욱 커져 클론 전쟁은 더 이상 걷잡을 수 없게 격화된다. 자신이 제다이가 될 것임을 굳게 믿고 있던 아나킨은 제다이 기사 자격을 줄 수 없다는 기사단의 결정에 절망하고, 스승인 오비완과의 갈등은 더욱 깊어만 간다. 그런 아나킨에게 자신의 정체를 드러낸 펠퍼틴은 절대적인 힘을 갖게 해 주겠다며 그를 유혹하고, 아나킨은 점점 어둠의 힘에 이끌려 변해가는데… 어둠의 세력에 완전히 장악당한 아나킨은 자신에게는 아버지나 다름없는 오비완 캐노비와 맞닥뜨리게 된다. 자신의 가족과도 같은 제자의 변절을 믿을 수 없는 오비완의 피를 토해내는 절규에도 아나킨은 흔들림이 없다. 결국 아나킨과 오비완은 화산 한 가운데에서 서로의 목에 칼을 겨누게 된다. 목숨을 건 슬픈 결투를 하게 된 그들의 운명은 비극적인 결말을 향해 치닫게 되는데…',
    '판타지',
    '["이완 맥그리거", "나탈리 포트만", "헤이든 크리스텐슨", "이언 맥디어미드", "프랭크 오즈"]',
    TO_DATE('2005-05-26', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    29,
    '인사이드 아웃',
    'https://femiwiki-uploaded-files-thumb.s3.amazonaws.com/7/78/IMG_3833.JPG/300px-IMG_3833.JPG',
    'https://www.youtube.com/embed/yRUAzGQ3nSY?mute=1&autoplay=1&controls=0',
    '모든 사람의 머릿속에 존재하는 감정 컨트롤 본부 그곳에서 불철주야 열심히 일하는 기쁨, 슬픔, 버럭, 까칠, 소심 다섯 감정들. 이사 후 새로운 환경에 적응해야 하는 라일리를 위해 그 어느 때 보다 바쁘게 감정의 신호를 보내지만 우연한 실수로 기쁨과 슬픔이 본부를 이탈하게 되자 라일리의 마음 속에 큰 변화가 찾아온다. 라일리가 예전의 모습을 되찾기 위해서는 ‘기쁨’과 ‘슬픔’이 본부로 돌아가야만 한다! 그러나 엄청난 기억들이 저장되어 있는 머릿속 세계에서 본부까지 가는 길은 험난하기만 한데… 과연, ‘라일리’는 다시 행복해질 수 있을까? 지금 당신의 머릿속에서 벌어지는 놀라운 일! 하루에도 몇번씩 변하는 감정의 비밀이 밝혀진다!',
    '판타지',
    '["에이미 포엘러", "필리스 스미스", "민디 캘링", "빌 헤이더", "루이스 블랙", "케이틀린 디아스", "카일 맥라클란", "다이안 레인"]',
    TO_DATE('2015-07-09', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    30,
    '시간을 달리는 소녀',
    'https://www.themoviedb.org/t/p/original/fTWFUDt9r6KOf3GYxh40NjEMklx.jpg',
    'https://www.youtube.com/embed/pPjLfq2bs8w?mute=1&autoplay=1&controls=0',
    '저 마코토에게는 남들에게 말 못할 비밀이 하나 있어요. 타임리프라고 하는 능력이죠.어느 날 우연히 그 능력을 가지게 됐어요.카즈야 이모 말에 따르면 내 또래 여학생들에게 자주 일어날 수 있는 일이라더군요.아무튼 그 능력 덕택에 학교 성적도 좋아지고,지각도 안하고 잦은 실수도 훨씬 줄어들었어요.세상 만사가 다 내 손안에 있는 느낌이었죠.친한 친구인 고스케와 치아키도 저의 변화가 싫지 않은 것 같아요. 매일 셋이서 야구놀이하며 즐거운 나날을 보낼 수 있을 것 같았죠. 그런데 느닷없이 치아키가 저에게 고백을 하는 거에요."마코토, 나랑 사귀지 않을래?" 전혀 남자로 보이지 않던 녀석인데 저는 깜짝 놀라고 말았어요. 어떻게든 그 고백을 없애기 위해 다시 과거로 돌아가 결국은 그 고백을 듣지 않게 되었어요. 하지만 과거로 돌아가면 돌아갈수록, 일이 점점 꼬여만 가요. 친구인 유리와 치아키가 연인 사이로 발전하는걸 지켜보려니 마음만 씁쓸하고, 고스케를 짝사랑하고 있던 후배 여학생의 고민상담까지 받은 저는 어떻게서든 두 사람을 이어주기 위해 과거에서 현재로 몇 번을 오갔는지 몰라요. 게다가 제가 당할 뻔한 사고를 대신 고스케가 당하는 불상사까지…타임리프로 사람의 마음을 내 멋대로 바꾼 벌을 받고 있나 봐요. 전 이제 어떡하면 좋을까요?',
    '판타지',
    '["나카 리이사", "이시다 타쿠야"]',
    TO_DATE('2007-06-14', 'YYYY-MM-DD'),
    'N',
    'Y');
    
insert into NETFLIX_MOVIE values(
    31,
    '다크 나이트',
    'https://ojsfile.ohmynews.com/STD_IMG_FILE/2015/0522/IE001832792_STD.jpg',
    'https://www.youtube.com/embed/EXeTwQWrcwY?mute=1&autoplay=1&controls=0',
    '정의로운 지방 검사 ‘하비 덴트’, ‘짐 고든’ 반장과 함께 범죄 소탕 작전을 펼치며 범죄와 부패로 들끓는 고담시를 지켜나가는 ‘배트맨’ 그러던 어느 날, 살아남기 위해 발버둥치던 범죄 조직은 배트맨을 제거하기 위해 광기어린 악당 ‘조커’를 끌어들이고 정체를 알 수 없는 조커의 등장에 고담시 전체가 깊은 혼돈 속으로 빠져든다. 급기야 배트맨을 향한 강한 집착을 드러낸 조커는 그가 시민들 앞에 정체를 밝힐 때까지 매일 새로운 사람들을 죽이겠다 선포하고 배트맨은 사상 최악의 악당 조커를 막기 위해 자신의 모든 것을 내던진 마지막 대결을 준비한다.',
    '액션',
    '["크리스찬 베일", "히스 레저", "아론 에크하트"]',
    TO_DATE('2008-08-06', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    32,
    '베테랑',
    'https://image.ytn.co.kr/general/jpg/2015/0608/201506081453003560_d.jpg',
    'https://www.youtube.com/embed/hcKp68DtBb0?mute=1&autoplay=1&controls=0',
    '한 번 꽂힌 것은 무조건 끝을 보는 행동파 ‘서도철’(황정민), 20년 경력의 승부사 ‘오팀장’(오달수), 위장 전문 홍일점 ‘미스봉’(장윤주), 육체파 ‘왕형사’(오대환), 막내 ‘윤형사’(김시후)까지 겁 없고, 못 잡는 것 없고, 봐 주는 것 없는 특수 강력사건 담당 광역수사대. 오랫동안 쫓던 대형 범죄를 해결한 후 숨을 돌리려는 찰나, 서도철은 재벌 3세 ‘조태오’(유아인)를 만나게 된다. 세상 무서울 것 없는 안하무인의 조태오와 언제나 그의 곁을 지키는 오른팔 ‘최상무’(유해진). 서도철은 의문의 사건을 쫓던 중 그들이 사건의 배후에 있음을 직감한다. 건들면 다친다는 충고에도 불구하고 포기하지 않는 서도철의 집념에 판은 걷잡을 수 없이 커져가고 조태오는 이를 비웃기라도 하듯 유유히 포위망을 빠져 나가는데… 베테랑 광역수사대 VS 유아독존 재벌 3세 2015년 여름, 자존심을 건 한판 대결이 시작된다!',
    '액션',
    '["황정민", "유아인", "유해진", "오달수"]',
    TO_DATE('2015-08-05', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    33,
    '미션 임파서블: 데드 레코닝 PART ONE',
    'https://i.namu.wiki/i/kV1g06FzQjtUrb87lXltSqGF4ClIxZJrnRRilMy2xmcqvZ3ZxPaS7Rc99Q_NDkkkO7JWjza9cTXICxSH6W41sQ.webp',
    'https://www.youtube.com/embed/F40MAXyXaac?mute=1&autoplay=1&controls=0',
    '가장 위험한 작전, 그의 마지막 선택 모든 인류를 위협할 새로운 무기를 추적하게 된 에단 헌트(톰 크루즈)와 IMF팀은 이 무기가 인류의 미래를 통제할 수 있다는 사실을 알게 된다. 전 세계가 위태로운 상황에 처한 가운데, 이를 추적하던 에단 헌트에게 어둠의 세력까지 접근하고 마침내 미스터리하고 강력한 빌런과 마주하게 된 그는 가장 위험한 작전을 앞두고 자신이 아끼는 사람들의 생명과 중요한 임무 사이에서 선택을 해야 하는 상황에 놓이게 되는데…',
    '액션',
    '["톰 크루즈"]',
    TO_DATE('2023-07-12', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    34,
    '분노의 질주: 라이드 오어 다이',
    'https://blog.kakaocdn.net/dn/cYP78k/btseqtBAPnM/raDDRQy0atp4uCteLGJySk/img.png',
    'https://www.youtube.com/embed/zmNRHgRG3lo?mute=1&autoplay=1&controls=0',
    '돔(빈 디젤)과 그의 패밀리 앞에 나타난 운명의 적 단테(제이슨 모모아). 과거의 그림자는 돔의 모든 것을 파괴하기 위해 달려온다. 단테에 의해 산산히 흩어진 패밀리들은 모두 목숨을 걸고 맞서야 하는 함정에 빠지고 마는데.. 달리거나 죽거나, 그들의 마지막 질주가 시작된다!',
    '액션',
    '["빈 디젤", "제이슨 모모아", "제이슨 스타뎀", "샤를리즈 테론", "브리 라슨", "미셸 로드리게즈", "성 강"]',
    TO_DATE('2023-05-17', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    35,
    '명량',
    'https://image.news1.kr/system/photos/2014/2/10/754660/article.jpg/dims/quality/80/optimize',
    'https://www.youtube.com/embed/spQtwggaCy4?mute=1&autoplay=1&controls=0',
    '1597년 임진왜란 6년, 오랜 전쟁으로 인해 혼란이 극에 달한 조선. 무서운 속도로 한양으로 북상하는 왜군에 의해 국가존망의 위기에 처하자 누명을 쓰고 파면 당했던 이순신 장군(최민식)이 삼도수군통제사로 재임명된다. 하지만 그에게 남은 건 전의를 상실한 병사와 두려움에 가득 찬 백성, 그리고 12척의 배 뿐. 마지막 희망이었던 거북선마저 불타고 잔혹한 성격과 뛰어난 지략을 지닌 용병 구루지마(류승룡)가 왜군 수장으로 나서자 조선은 더욱 술렁인다. 330척에 달하는 왜군의 배가 속속 집결하고 압도적인 수의 열세에 모두가 패배를 직감하는 순간, 이순신 장군은 단 12척의 배를 이끌고 명량 바다를 향해 나서는데…! 12척의 조선 vs 330척의 왜군 역사를 바꾼 위대한 전쟁이 시작된다!',
    '액션',
    '["최민식", "류승룡", "조진웅"]',
    TO_DATE('2014-07-30', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    36,
    '존 윅4',
    'https://i.namu.wiki/i/NdikfqD9ep_gcMtGFO8Yn1aDyW17YTS5a85qrYiKsnGD_cOudNNV34xJTuVYXvG3ci6eD0Bko8m1Qmep0VWuOg.webp',
    'https://www.youtube.com/embed/ly3QrgEZaQY?mute=1&autoplay=1&controls=0',
    '죽을 위기에서 살아난 ‘존 윅’은 ‘최고 회의’를 쓰러트릴 방법을 찾아낸다. 비로소 완전한 자유의 희망을 보지만, NEW 빌런 ‘그라몽 후작’과 전 세계의 최강 연합은 ‘존 윅’의 오랜 친구까지 적으로 만들어 버리고, 새로운 위기에 놓인 ‘존 윅’은 최후의 반격을 준비하는데,, 레전드 액션 블록버스터 <존 윅>의 새로운 챕터가 열린다!',
    '액션',
    '["키아누 리브스", "견자단", "빌 스카스가드", "로렌스 피시번", "이안 맥쉐인", "사나다 히로유키"]',
    TO_DATE('2023-04-12', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    37,
    '암살',
    'https://blog.kakaocdn.net/dn/dMPXKY/btr5t9FsBM2/ZhQEA1JJFt3ob5jh8wU7ak/img.png',
    'https://www.youtube.com/embed/RnGxpZ75zFU?mute=1&autoplay=1&controls=0',
    '1933년 조국이 사라진 시대 대한민국 임시정부는 일본 측에 노출되지 않은 세 명을 암살작전에 지목한다. 한국 독립군 저격수 안옥윤, 신흥무관학교 출신 속사포, 폭탄 전문가 황덕삼! 김구의 두터운 신임을 받는 임시정부 경무국 대장 염석진은 이들을 찾아 나서기 시작한다. 암살단의 타깃은 조선주둔군 사령관 카와구치 마모루와 친일파 강인국. 한편, 누군가에게 거액의 의뢰를 받은 청부살인업자 하와이 피스톨이 암살단의 뒤를 쫓는데... 친일파 암살작전을 둘러싼 이들의 예측할 수 없는 운명이 펼쳐진다!',
    '액션',
    '["전지현", "이정재", "하정우"]',
    TO_DATE('2015-07-22', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    38,
    '테넷',
    'https://isplus.com/data/isp/image/2020/07/13/isp1cf33940-bf50-410b-8ac2-c0dd9cd31955.jpg',
    'https://www.youtube.com/embed/LdOM0x0XDMo?mute=1&autoplay=1&controls=0',
    '시간의 흐름을 뒤집는 인버전을 통해 현재와 미래를 오가며 세상을 파괴하려는 사토르(케네스 브래너)를 막기 위해 투입된 작전의 주도자(존 데이비드 워싱턴). 인버전에 대한 정보를 가진 닐(로버트 패틴슨)과 미술품 감정사이자 사토르에 대한 복수심이 가득한 그의 아내 캣(엘리자베스 데비키)과 협력해 미래의 공격에 맞서 제3차 세계대전을 막아야 한다!',
    '액션',
    '["존 데이비드 워싱턴", "로버트 패틴슨", "엘리자베스 데비키"]',
    TO_DATE('2020-08-26', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    39,
    '군도',
    'https://an2-img.amz.wtchn.net/image/v2/gPQbSPeJntyowGEE3ijpcA.jpg?jwt=ZXlKaGJHY2lPaUpJVXpJMU5pSjkuZXlKdmNIUnpJanBiSW1SZk56STVlREV3T0RCeE9EQWlYU3dpY0NJNklpOTJNaTl6ZEc5eVpTOXBiV0ZuWlM4eE5qTTBNREV6TXpJM01USXpNRE16TVRrNUluMC5ZNnFwbFRZRS1SMGhBb1hvUThndDAzMTJGem5sMHpHaFBpa3BMWkhqQWhJ',
    'https://www.youtube.com/embed/heN3kc7yz7o?mute=1&autoplay=1&controls=0',
    '군도, 백성을 구하라! 양반과 탐관오리들의 착취가 극에 달했던 조선 철종 13년. 힘 없는 백성의 편이 되어 세상을 바로잡고자 하는 의적떼인 군도(群盜), 지리산 추설이 있었다. 쌍칼 도치 vs 백성의 적 조윤 잦은 자연재해, 기근과 관의 횡포까지 겹쳐 백성들의 삶이 날로 피폐해 져 가는 사이, 나주 대부호의 서자로 조선 최고의 무관 출신인 조윤은 극악한 수법으로 양민들을 수탈, 삼남지방 최고의 대부호로 성장한다. 한편 소, 돼지를 잡아 근근이 살아가던 천한 백정 돌무치는 죽어도 잊지 못할 끔찍한 일을 당한 뒤 군도에 합류. 지리산 추설의 신 거성(新 巨星) 도치로 거듭난다. 뭉치면 백성, 흩어지면 도적! 망할 세상을 뒤집기 위해, 백성이 주인인 새 세상을 향해 도치를 필두로 한 군도는 백성의 적, 조윤과 한 판 승부를 시작하는데...',
    '액션',
    '["하정우", "강동원"]',
    TO_DATE('2014-07-23', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    40,
    '베를린',
    'https://img.sbs.co.kr/newsnet/etv/upload/2013/01/11/30000214705_700.jpg',
    'https://www.youtube.com/embed/8tUQ4fblukM?mute=1&autoplay=1&controls=0',
    '거대한 국제적 음모가 숨겨진 운명의 도시 베를린. 그 곳에 상주하는 국정원 요원 정진수는 불법무기거래장소를 감찰하던 중 국적불명, 지문마저 감지되지 않는 일명 ‘고스트’ 비밀요원 표종성의 존재를 알게 된다. 그의 정체를 밝혀내기 위해 뒤를 쫓던 정진수는 그 배후에 숨겨진 엄청난 국제적 음모를 알게 되면서 걷잡을 수 없는 위기에 빠진다. 한편 표종성을 제거하고 베를린을 장악하기 위해 파견된 동명수는 그의 아내 연정희를 반역자로 몰아가며 이를 빌미로 숨통을 조이고, 표종성의 모든 것에 위협을 가한다. 표종성은 동명수의 협박 속에서 연정희의 무죄를 증명하기 위해서 그녀를 미행하게 되지만, 예상치 못한 아내의 비밀을 알게 되면서 혼란에 휩싸이게 되는데... 국제적 음모와 각자의 목적에 휘말려 서로를 쫓는 이들의 숨막히는 추격전! 2013년, 초대형 액션 프로젝트가 펼쳐진다!',
    '액션',
    '["하정우", "한석규", "류승범", "전지현", "이경영"]',
    TO_DATE('2013-01-30', 'YYYY-MM-DD'),
    'N',
    'Y');
    
insert into NETFLIX_MOVIE values(
    41,
    '어벤져스: 엔드게임',
    'https://i.namu.wiki/i/8_vUuuBdc-LDWGmVU_d6q8yXykSpF6dw3Ya2w2O36GKBBUIvkI3H9JRd1qRAsn-FsaAxJx8N9VV_7O0W9sbTsA.webp',
    'https://www.youtube.com/embed/Ko2NWhXI9e8?mute=1&autoplay=1&controls=0',
    '인피니티 워 이후 절반만 살아남은 지구 마지막 희망이 된 어벤져스 먼저 떠난 그들을 위해 모든 것을 걸었다! 위대한 어벤져스 운명을 바꿀 최후의 전쟁이 펼쳐진다!',
    'SF',
    '["로버트 다우니 주니어", "크리스 에반스", "크리스 헴스워스", "마크 러팔로", "스칼릿 조핸슨", "제레미 레너", "폴러드", "돈 치들", "브리 라슨", "카렌 길런"]',
    TO_DATE('2019-04-24', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    42,
    '그래비티',
    'https://www.themoviedb.org/t/p/original/aq01FtuCkjdVfSFbuYZSXIm35RD.jpg',
    'https://www.youtube.com/embed/ufsrgE0BYf0?mute=1&autoplay=1&controls=0',
    '허블 우주망원경을 수리하기 위해 우주를 탐사하던 라이언 스톤 박사는 폭파된 인공위성의 잔해와 부딪히면서 소리도 산소도 없는 우주 한 가운데에 홀로 남겨지는데…',
    'SF',
    '["산드라 블록", "조지 클루니"]',
    TO_DATE('2013-10-17', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    43,
    '매트릭스: 리저렉션',
    'https://www.wolyo.co.kr/news/photo/201407/22427_18235_2549.jpg',
    'https://www.youtube.com/embed/yEPgqn5f1uk?mute=1&autoplay=1&controls=0',
    '토마스 앤더슨은 ‘자신’의 현실이 물리적 구성개념인지 아니면 정신적 구성개념인지 알아내기 위해 이번에도 흰 토끼를 따라가야 한다. 토마스, 아니 네오가 배운 게 있다면 비록 환상이라 할지라도 선택이야말로 매트릭스를 탈출할 유일한 길이라는 것이다. 물론 네오는 무엇을 해야 할지 이미 알고 있다. 그가 아직 모르는 사실은 이 새로운 버전의 매트릭스가 그 어느 때보다도 강력하고, 확고부동하고, 위험하다는 것이다. 평범한 일상과 그 이면에 놓여 있는 또 다른 세계, 두 개의 현실이 존재하는 세상에서 운명처럼 인류를 위해 다시 깨어난 구원자 네오. 빨간 약과 파란 약 중 어떤 것을 선택할 것인가?',
    'SF',
    '["키아누 리브스", "캐리 앤 모스"]',
    TO_DATE('2021-12-22', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    44,
    '인터스텔라',
    'https://cdn.sisasinmun.com/news/photo/201305/67034_68414_616.jpg',
    'https://www.youtube.com/embed/zSWdZVtXT7E?mute=1&autoplay=1&controls=0',
    '세계 각국의 정부와 경제가 완전히 붕괴된 미래가 다가온다. 지난 20세기에 범한 잘못이 전 세계적인 식량 부족을 불러왔고, NASA도 해체되었다. 이때 시공간에 불가사의한 틈이 열리고, 남은 자들에게는 이 곳을 탐험해 인류를 구해야 하는 임무가 지워진다. 사랑하는 가족들을 뒤로 한 채 인류라는 더 큰 가족을 위해, 그들은 이제 희망을 찾아 우주로 간다. 그리고 우린 답을 찾을 것이다. 늘 그랬듯이…',
    'SF',
    '["매튜 맥커너히", "앤 해서웨이", "마이클 케인", "제시카 차스테인"]',
    TO_DATE('2014-11-06', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    45,
    '더 문',
    'https://img9.yna.co.kr/etc/inner/KR/2018/05/11/AKR20180511084000005_01_i_P2.jpg',
    'https://www.youtube.com/embed/0uXSVdzFgNg?mute=1&autoplay=1&controls=0',
    '2029년, 대한민국의 달 탐사선 우리호가 달을 향한 여정에 나선다. 위대한 도전에 전 세계가 주목하지만 태양 흑점 폭발로 인한 태양풍이 우리호를 덮치고 ‘황선우’(도경수) 대원만이 홀로 남겨진다. 대한민국의 우주선이 달로 향한 것이 이번이 처음은 아니었다. 5년 전, 원대한 꿈을 안고 날아올랐지만 모두가 지켜보고 있는 가운데 공중 폭발로 산산이 부서졌던 나래호. 또다시 일어난 비극에 유일한 생존자인 선우를 지키기 위해 나로 우주센터 관계자들과 정부는 총력을 다하고 온 국민이 그의 생존을 염원한다. 선우를 무사 귀환시키기 위해서 5년 전 나래호 사고의 책임을 지고 산에 묻혀 지내던 전임 센터장 ‘김재국’(설경구)이 다시 합류하지만, 그의 힘만으로는 역부족이다. 선우를 구출할 또 다른 희망인 NASA 유인 달 궤도선 메인 디렉터 ‘윤문영’(김희애)에게 도움을 청해보지만 그마저 쉽지 않다. 재국은 또다시 누군가를 잃지 않기 위해 마지막으로 자신의 모든 것을 걸어 보는데…. 우주에 홀로 고립된 대원과 그의 무사 귀환에 모든 것을 건 남자 살기 위한, 살려내기 위한 고군분투가 시작된다.',
    'SF',
    '["설경구", "도경수", "박병은", "조한철", "최병모", "홍승희"]',
    TO_DATE('2023-08-02', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    46,
    '설국열차',
    'https://cloudfront-ap-northeast-1.images.arcpublishing.com/chosun/NMVOO67K3X3RJ2ASYOSLEVRSWA.jpg',
    'https://www.youtube.com/embed/xVh3bh0N68Q?mute=1&autoplay=1&controls=0',
    '기상 이변으로 모든 것이 꽁꽁 얼어붙은 지구. 살아남은 사람들을 태운 기차 한 대가 끝없이 궤도를 달리고 있다. 춥고 배고픈 사람들이 바글대는 빈민굴 같은 맨 뒤쪽의 꼬리칸, 그리고 선택된 사람들이 술과 마약까지 즐기며 호화로운 객실을 뒹굴고 있는 앞쪽칸. 열차 안의 세상은 결코 평등하지 않다. 기차가 달리기 시작한 17년 째, 꼬리칸의 젊은 지도자 커티스는 긴 세월 준비해 온 폭동을 일으킨다. 기차의 심장인 엔진을 장악, 꼬리칸을 해방시키고 마침내 기차 전체를 해방 시키기 위해 절대권력자 윌포드가 도사리고 있는 맨 앞쪽 엔진칸을 향해 질주하는 커티스와 꼬리칸 사람들. 그들 앞에 예기치 못한 상황들이 기다리고 있는데…',
    'SF',
    '["크리스 에반스", "송강호", "애드 해리스", "존 허트", "틸다 스윈튼", "제이미 벨", "옥타비아 스펜서", "이완 브렘너", "알리슨 필", "고아성"]',
    TO_DATE('2013-08-01', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    47,
    '아이언맨 3',
    'https://play-lh.googleusercontent.com/DV_MgeteY8oCcqCE4qZPVN6O3boel8348_imNLIBgMZHfPv-6hYrjp3KiUyk0Cm1Vi9B-A=w240-h480-rw',
    'https://www.youtube.com/embed/Ke1Y3P9D0Bc?mute=1&autoplay=1&controls=0',
    '<어벤져스> 뉴욕 사건의 트라우마로 인해 영웅으로서의 삶에 회의를 느끼는 토니 스타크(로버트 다우니 주니어). 그가 혼란을 겪는 사이 최악의 테러리스트 만다린(벤 킹슬리)을 내세운 익스트리미스 집단 AIM이 스타크 저택에 공격을 퍼붓는다. 이 공격으로 그에게 남은 건 망가진 수트 한벌 뿐. 모든 것을 잃어버린 그는 다시 테러의 위험으로부터 세계와 사랑하는 여인(기네스 팰트로)를 지켜내야 하는 동시에 머릿속을 떠나지 않던 한가지 물음의 해답도 찾아야만 한다. 과연 그가 아이언맨인가? 수트가 아이언맨인가?',
    'SF',
    '["로버트 다우니 주니어", "기네스 팰트로", "벤 킹슬리", "돈 치들", "가이 피어스", "레베카 홀"]',
    TO_DATE('2013-04-25', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    48,
    '매드 맥스: 분노의 도로',
    'https://joyposter.cafe24.com//NEW-posters/850X850Poster/SMD-018.jpg',
    'https://www.youtube.com/embed/p-fDEhVBnv4?mute=1&autoplay=1&controls=0',
    '핵전쟁으로 멸망한 22세기. 얼마 남지 않은 물과 기름을 차지한 독재자 임모탄 조가 살아남은 인류를 지배한다. 한편, 아내와 딸을 잃고 살아남기 위해 사막을 떠돌던 맥스(톰 하디)는 임모탄의 부하들에게 납치되어 노예로 끌려가고, 폭정에 반발한 사령관 퓨리오사(샤를리즈 테론)는 인류 생존의 열쇠를 쥔 임모탄의 여인들을 탈취해 분노의 도로로 폭주한다. 이에 임모탄의 전사들과 신인류 눅스(니콜라스 홀트)는 맥스를 이끌고 퓨리오사의 뒤를 쫓는데... 끝내주는 날, 끝내주는 액션이 폭렬한다!',
    'SF',
    '["톰 하디", "샤를리즈 테론", "니콜라스 홀트"]',
    TO_DATE('2015-05-14', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    49,
    '월드 워 Z',
    'https://ppss.kr/wp-content/uploads/2013/06/%EC%9B%94%EB%93%9C%EC%9B%8Cz_005.jpg',
    'https://www.youtube.com/embed/0Ww2LQsG07c?mute=1&autoplay=1&controls=0',
    '전 세계 이상 기류… 거대한 습격이 시작된다! 의문의 항공기 습격, 국가별 입국 전면 통제, 국경선을 둘러싼 높은 벽, 세계 곳곳에서 원인을 알 수 없는 이변이 일어나기 시작한다. 그리고 정체불명 존재들의 무차별적 공격으로 도시는 순식간에 아수라장으로 변한다. 인류의 대재난에 맞설 최후의 적임자, 제리 군인 출신으로 전시 경험이 풍부하고 위기 대처 능력이 뛰어난 UN 소속 조사관 제리는 위험한 상황에서 가까스로 가족들과 탈출하는데 성공하고 이제껏 본적 없는 인류 최대의 위기 앞에 대재난에 맞설 최후의 적임자로 지목된다 생존률 제로, 최후의 카운트다운이 시작된다! 마침내 제리는 전 세계를 위협하는 거대한 정체들과 직면하게 되고, 그들의 끊임없는 공격에 맞서 필사의 사투를 벌이게 되는데… 과연 누구도 살아남을 수 없는 인류 최후의 대재난을 막아낼 수 있을 것인가!',
    'SF',
    '["브래드 피트", "미레유 에노스"]',
    TO_DATE('2013-06-20', 'YYYY-MM-DD'),
    'N',
    'Y');
insert into NETFLIX_MOVIE values(
    50,
    '엑스맨: 퍼스트 클래스',
    'https://i.namu.wiki/i/WloFRGZCQBx4hUjajXGzxvN1xXnT2hWjL8ggQDTQzTo7aDiurQuZfx_QgMkadauBBGpd19gL0YDSTqp1d_KLow.webp',
    'https://www.youtube.com/embed/XKF6J6kgs0s?mute=1&autoplay=1&controls=0',
    '찰스 자비에와 에릭 랜셔가 각각 ‘프로페서 X’와 ‘매그니토’라는 이름을 얻기 전 1960년대 ‘냉전 시대’. 이상적인 환경에서 자라 유전자학을 공부하는 찰스는 자신에게 특별한 텔레파시 능력이 있음을 깨닫고 ‘돌연변이’의 존재에 대해 자각하기 시작한다. 그러던 중, 주변의 금속을 마음대로 제어할 수 있는 강력한 능력을 가진 에릭을 만나 절친한 친구가 된다. 반면, 인류를 지배하려는 집단 ‘헬파이어 클럽’의 수장 세바스찬 쇼우는 미국과 러시아 간의 핵전쟁을 도발해서 3차 세계대전을 일으키려 하고, 이들을 막기 위해 CIA에서는 찰스와 에릭에게 도움을 요청하게 된다. 의기투합한 두 사람은 세계 각지를 돌며 때론 ‘다르다’는 이유로, 혹은 안전하지 못하다고 차별 받는 돌연변이들을 규합하고 ‘헬파이어 클럽’에 대항하는 엑스맨 팀을 만들기 시작한다. 그 와중에 돌연변이와 인간이 평화롭게 공존할 수 있다고 믿는 찰스와 달리, 에릭은 전쟁 중에 일어난 비극적인 과거사로 인간에 대한 불신의 골이 깊어 이들의 우정은 서서히 흔들리게 된다. 인간과 돌연변이의 갈등은 점점 치솟기 시작하는 와중에 세계 3차 대전을 막기 위해서 엑스맨과 헬파이어 클럽 간의 인류의 운명을 건 거대한 전쟁이 시작된다!',
    'SF',
    '["제임스 맥어보이", "마이클 패스벤더", "케빈 베이컨", "제니퍼 로렌스", "재뉴어리 존스"]',
    TO_DATE('2011-06-02', 'YYYY-MM-DD'),
    'N',
    'Y');
    
    
    commit;
    
    
select * from netflix_movie where movie_id <=10 and movie_id >=0  ;
------------------

drop table NETFLIX_QA_BOARD cascade constraints;

create table NETFLIX_QA_BOARD(
    qa_board_no number primary key,
    qa_board_write varchar2(50), --member_profile_name fk
    qa_board_title varchar2(200),
    qa_board_content varchar2(500),
    qa_board_created_date date default sysdate,
    qa_board_modified_date date default sysdate,
    qa_board_hit number
);


---------------------------------

drop table NETFLIX_FAVORITES cascade constraints;

CREATE TABLE NETFLIX_FAVORITES(
    favorites_member_id varchar2(60) not null,
    favorites_member_profile_name VARCHAR2(50) NOT NULL,
    favorites_movie_id NUMBER NOT NULL,
    constraint ix_favorites_member UNIQUE(favorites_member_id, favorites_member_profile_name),
    CONSTRAINT fk_fav_movie
        FOREIGN KEY (favorites_movie_id) REFERENCES NETFLIX_MOVIE(movie_id)
        ON DELETE CASCADE
);

--------------------------------

-- drop table NETFLIX_SOCIAL_ACCOUNT cascade constraints;

-- create table NETFLIX_SOCIAL_ACCOUNT(
--     social_account_member_email varchar2(50), -- fk
--     social_account_member_social varchar2(10), -- fk
    
--      constraint ix_netflix_social_account UNIQUE(social_account_member_email, social_account_member_social),
--     constraint fk_social_account_member_email
--         foreign key (social_account_member_email) references NETFLIX_MEMBER(member_email)
--         on delete cascade
-- );

--------------------------------

drop table NETFLIX_SOCIAL cascade constraints;

create table NETFLIX_SOCIAL(
    social_no number primary key,
    social_provider varchar2(10) not null unique
);

-- alter table netflix_social_account
--     add constraint fk_SA_member_social
--     foreign key (social_account_member_social) references NETFLIX_SOCIAL(social_provider)
-- on delete cascade;

insert into netflix_social values( (select nvl(max(social_no),0)+1 from netflix_social), 'none');
insert into netflix_social values( (select nvl(max(social_no),0)+1 from netflix_social), 'kakao');    
insert into netflix_social values( (select nvl(max(social_no),0)+1 from netflix_social), 'naver');    
insert into netflix_social values( (select nvl(max(social_no),0)+1 from netflix_social), 'google');

alter table netflix_member
    add constraint fk_member_social
    foreign key (member_social) references NETFLIX_SOCIAL(social_provider)
on delete cascade;


commit;

------------------




select * from all_indexes;
drop index ix_netflix_auth;

select * from netflix_order;

select * from (select rownum rnum, o.* from netflix_order o where order_member_id = 'tatelulove4@naver.com_kakao' and rownum<=2 
order by order_id desc) where rnum = 2;



select * from NETFLIX_MEMBER m, NETFLIX_MEMBER_PROFILE mp where m.member_id = mp.member_profile_member_id and m.member_id = 'seralove4@gmail.com';

select * from netflix_order where order_member_id = 'seralove4@gmail.com';


select * from NETFLIX_MEMBER;
select * from NETFLIX_AUTH;
select * from NETFLIX_MEMBER_PROFILE;
select * from NETFLIX_ORDER;
select * from NETFLIX_MEMBERSHIP;
select * from NETFLIX_MOVIE;
select * from NETFLIX_QA_BOARD;
select * from NETFLIX_FAVORITES;
-- select * from NETFLIX_SOCIAL_ACCOUNT;
select * from netflix_social;



delete from netflix_member where member_id = 'seralove4@gmail.com';


commit;

delete from netflix_member_profile where member_profile_member_id = 'tatelulove4@naver.com_kakao';


--insert into netflix_member_profile values('tatelulove4@naver.com_kakao', '테스트1');
--insert into netflix_member_profile values('tatelulove4@naver.com_kakao', '테스트2');


select * from netflix_member mb, netflix_auth au, netflix_member_profile mp
    where mb.member_id = au.auth_member_id and mb.member_id = mp.member_profile_member_id and mb.member_id = 'user@example.com';

select * from netflix_member mb, netflix_auth au, netflix_member_profile mp
    where mb.member_id = au.auth_member_id and mb.member_id = 'tatelulove4@naver.com_kakao' and mb.member_id = mp.member_profile_member_id;

select * from netflix_member mb, netflix_auth au
    where mb.member_id = au.auth_member_id and mb.member_id = 'tatelulove4@naver.com_naver';

--update netflix_member set member_password = '$2a$10$tbmvHLrNpUmlMkj5i8FLl.QASVkazZfRsapVH7lo8xTAPFYVgG72C'
--    where member_id= 'tatelulove4@naver.com_kakao';
--
--update netflix_order set order_valid = 'E';

select * from netflix_member mb, netflix_auth au, netflix_member_profile mp
    where mb.member_id = au.auth_member_id and mb.member_id = 'seralove4@gmail.com_google' and mb.member_id = mp.member_profile_member_id;

--------------------
delete from netflix_member where member_id = 'seralove4@gmail.com_google';
delete from netflix_member where member_email = 'user@example.com';
delete from netflix_member where member_id = 'tatelulove4@naver.com_naver';
delete from netflix_member where member_id = 'tatelulove4@naver.com_kakao';