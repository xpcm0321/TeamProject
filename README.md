# GAONNURI PROJECT
> - 소설, 시, 에세이 등 분야를 막론하고 독서 활동을 애호하는 조원들이 모여 기획하게 된 프로젝트입니다.
> - 실제 유저의 독후감으로 다양한 책들에 대한 접근을 더욱 용이하게 하고, 소비를 유도할 수 있는 **유저 중심 커뮤니티 사이트**를 계획했습니다.

---

## 프로젝트 소개
유저들이 **책에 대한 리뷰**를 쓰고 공유할 수 있는 게시판으로 하여금 **소비를 촉진**하고,
**개인의 선호에 맞추어 독서**를 즐길 수 있도록 **추천 독서 목록을 제공**하는 커뮤니티 사이트

---

## 관련문서
- [화면 설계안 (피그마)](https://www.figma.com/design/yFBHOXhBbQU1VXc9GmkSgq/%ED%99%94%EB%A9%B4-%EC%84%A4%EA%B3%84?node-id=0-1&t=3GaRwQ9M9rcqdYgo-1)
- [PPT 발표 자료(CANVA)](https://www.canva.com/design/DAGloWvHsP4/8h46aEslUAnyokPI_-G3Qw/edit?utm_content=DAGloWvHsP4&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton)

---

## 주요기능

| 분류 | 기능 설명 |
|------|-----------|
| **사용자 기능** | - 회원가입 / 로그인 / 소셜 로그인 (카카오, 구글)<br> - 비밀번호 변경 및 탈퇴<br> - 이메일 인증 |
| **책 등록 기능** | - NAVER BOOK API를 활용해 Bookdata에 책 데이터 등록 |
| **리뷰 기능** | - DB에 등록되어있는 책들을 보고 별점 및 리뷰 제목, 리뷰 내용 작성 가능 |
| **유저 친화 기능** | - 마이페이지 내에서 프로필 이미지 변경 가능<br> - 좋아요 단 리뷰, 내가 쓴 리뷰 목록 열람 가능 |

---

## 기여자

| 이름 | 역할 |
|------|------|
| 김경미 (팀장) | **데이터베이스 총괄 관리 및 초기 설계**<br> - MySQL DB 및 테이블 설계, 관계 설정<br> - View 연결 및 Bookdata 관련 스케줄러 구현 |
| 박지윤 | **회원 기능 및 소셜 로그인 담당**<br> - Member DB 및 View 구현<br> - Google/Naver/Kakao 로그인, 계정 탈퇴 등 구현<br> - OpenAI 기반 랜덤 닉네임 생성 기능 구현 |
| 이수정 | **리뷰 시스템 전반 담당**<br> - 리뷰 DB 설계 및 View 구현<br> - 리뷰 작성, 수정, 삭제 등 CRUD 기능 구현<br> - 메인 페이지 전체 리뷰 View 구성 |
| 최예진 | **외부 도서 API 연동 및 Bookdata 관리**<br> - Naver Book API 연동 구현<br> - Bookdata 저장 및 View 구성 |

---
## 협업 도구

- Git / GitHub  
  버전 관리 및 코드 공유, PR 리뷰 및 브랜치 전략 사용

- Sourcetree  
  Git GUI 툴로 브랜치 시각화 및 커밋 내역 관리

- KakaoTalk  
  빠른 피드백 및 일정 조율을 위한 실시간 커뮤니케이션

- Discord  
  음성 회의 및 화면 공유를 통한 원격 회의 진행

- 오프라인 미팅  
  주요 기획 회의 및 설계 논의는 직접 만나서 진행
  
---