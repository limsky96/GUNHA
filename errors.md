1. java 관련 멈춤 해결
  - redhat에서 참조하는 jdk와(jre에 있는 jdk17) spring boot에서 참조하는 jdk설정을 바꿔주니 정상 동작
  - 물론 17, 11을 이용했고 javahome이랑 default jdk도 잡아줌.. 천천히 껐다 키면 이제 정상 동작함.. 미치는 줄 알았네
  - 이제 vscode에서도 유용하게 java build가능!

2. maven setting에서 http-blocker mirror태그 주석처리
  - 안하면 자동으로 막음.. maven문제
  - 아니면 그냥 ojdbc 10인가 8 동작하는 것 있기 때문에 그거 쓰면 됨 ^^
  

3. jsp 파일에서 auto close안 되던 것 해결 -> 그냥 auto close 적용 확장자에 jsp추가
  - auto rename은 적용 돼 있음 
  - 하지만 c태그 등 자잘한 오류를 잡을 수 없다.. 그러므로 thymeleaf나 mustache를 사용하자
  - jsp 문법을 thymeleaf나 mustache로 바꾸기 -> gpt가 해줄거야


4. sql developer 연결 해두면 test가 안 됨..


5. builder를 사용하면 VO에서 List를 return 받기 위해 mapper.xml에서 다른 방식으로 설정해줘야함

<!-- <resultMap id="userMap" type="edu.global.demo.vo.UserVO">
  <constructor>
    <idArg column="member_id" javaType="java.lang.String" />
    <arg column="member_email" javaType="java.lang.String" />
    <arg column="member_password" javaType="java.lang.String" />
    <arg column="member_card_number" javaType="java.lang.String" />
    <arg column="member_membership_no" javaType="int" />
    <arg column="member_social" javaType="java.lang.String" />
  </constructor>
  <collection property="authList" resultMap="authMap"></collection>
</resultMap> -->
이런 방식으로..?


6. 소셜 로그인 Autowired해도 되고 아니면 SecurityContextHolder에 넣어줄 때 authorities도 넣어준다..


