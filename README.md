# schedule_calendar


## 메인 페이지 

|<img src="https://github.com/woowoosik/Calendar/assets/49232649/017b028f-bed1-4472-a60b-4dc0e938cd99" width="200" height="400"/>|
|------|
|메인 달력 이동|

메인 페이지는 `Joda-time`라이브러리를 사요하여 날짜 데이터를 이용하여 달력형태를 만들었습니다.

일정에 관한 시각화는 가로방향의 막대형태로 표현하였고 하루의 최대 5줄을 볼 수 있고, 그 이상이면 +N개로 볼 수 있도록 하였습니다. 

스크롤로 월별로 볼 수 있으며 우상단에 일정 추카버튼을 만들었습니다.

디자인 패턴은 `MVVM`, 의존성 부여는 `hilt`를 사용하였고, `코루틴`과 `ROOM`을 사용하여 데이터를 저장하고 관리합니다.


## CRUD (kakao map, kakao 키워드)

#### ROOM


데이터베이스로는 ROOM을 사용하여 데이터를 저장하였습니다.

입력되는 값으로는 제목, 내용, 시간, 메인캘린더에 가로방향 바 형태의 색과 텍스트 색, 지도를 보여줄 경도,위도가 있습니다.




#### 일정 추가 (kakao API)

|<img src="https://github.com/woowoosik/Calendar/assets/49232649/ff1edded-544c-4079-81c2-a932d6c446a6" width="200" height="400"/>|
|------|
|일정 추가|

기본적인 제목과 내용을 추가하고, `retrofit`과 `kakao api`를 이용하여 지도를 추가하는 기능을 구현하였습니다.

날짜는 data picker 선택할 수 있게 하였고, color는 `ambilwarna` color picker 라이브러리를 사용아여 메인에 나올 스케줄 바의 컬러을 선택하는 기능을 추가하였습니다.





#### 일정 수정 및 삭제

|<img src="https://github.com/woowoosik/Calendar/assets/49232649/d9a47379-0fe4-4767-8089-35d152c8fe06" width="200" height="400"/>|
|------|
|일정 수정 및 삭제|

삭제는 recycler에서 왼쪽 방향으로 당길 시에 나오는 삭제 버튼으로 일정을 삭제할 수 있습니다. 

수정은 수정 페이지로 이동하고 수정을 할 수 있습니다.
