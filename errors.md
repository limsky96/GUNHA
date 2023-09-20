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

  -> NoArgsConstructor annotation 추가해주면 해결.(아니면 default 생성자를 만들든가)


6. 소셜 로그인 Autowired해도 되고 아니면 SecurityContextHolder에 넣어줄 때 authorities도 넣어준다..


7. 크롬 개발자 도구 문제
   - 이유는 모르겠지만 jsSwiper를 이용할 때 크롬 개발자 도구에서 반응형이 제대로 동작하지 않는다.(예로 만약 breakPoints를 500으로 줬을 시, 125px에서 동작을 한다.. 원인 불명)




8. 결제모듈 참고
   - https://admin.portone.io 여기는 rest api key 등이 있는 페이지
   - https://developers.portone.io/docs/ko/readme 여기는 developers docs 페이지

<!-- 결제 시작부터 끝까지 -->
<!-- 토큰 받고 -->
<!--    app.post("/iamport-callback/schedule", async (req, res) => {
      try {
        const { imp_uid, merchant_uid } = req.body;
        // 액세스 토큰(access token) 발급 받기
        const getToken = await axios({
          url: "https://api.iamport.kr/users/getToken",
          method: "post", // POST method
          headers: { "Content-Type": "application/json" }, 
          data: {
            imp_key: "imp_apikey", // REST API 키
            imp_secret: "ekKoeW8RyKuT0zgaZsUtXXTLQ4AhPFW3ZGseDA6bkA5lamv9OqDMnxyeB9wqOsuO9W3Mx9YSJ4dTqJ3f" 
          }
        });
        const { access_token } = getToken.data; // 인증 토큰
        // imp_uid로 포트원 서버에서 결제 정보 조회
        const getPaymentData = await axios({
          url: `https://api.iamport.kr/payments/${imp_uid}`, // imp_uid 전달
          method: "get", // GET method
          headers: { "Authorization": access_token } 
        });
        const paymentData = getPaymentData.data; // 조회한 결제 정보
        const { status } = paymentData;
        if (status === "paid") { // 결제 성공적으로 완료
          // DB에 결제 정보 저장
          await Orders.findByIdAndUpdate(merchant_uid, { $set: paymentData }); // Mongoose
          ...
          axios({
            url: "{결제예약을 받을 서비스 URL}", 
            method: "post",
            // 인증 토큰 Authorization header에 추가
            headers: { "Authorization": access_token }, 
            data: {
              customer_uid: "gildong_0001_1234", // 카드(빌링키)와 1:1로 대응하는 값
              schedules: [
                {
                  // 주문 번호
                  merchant_uid: "order_monthly_0001", 
                  // 결제 시도 시각 in Unix Time Stamp. 예: 다음 달 1일
                  schedule_at: 1519516800, 
                  amount: 8900,
                  name: "월간 이용권 정기결제",
                  ...
                }
              ]
            }
          });
        } else {
          // 재결제 시도
        }
      } catch (e) {
        res.status(400).send(e);
      }
    }); -->

9. thymeleaf layout dialect 적용..
   - 적용이 아예 안 되던 문제가 있었는데 clean java language server workspace를 해주니 (캐시를 날리니) 해결.
   - head부분에 layout:fragment로 css를 적용해두고 다시 작성을 하면 왜인지 모르겠지만 코드가 중복된다. <- 이유는 찾는 중. layout 파일에는 head에 놓고 자식 파일에는 body에서 구현한 후에 집어 넣으니 중복되지 않음.. 이유는 모르겠음.