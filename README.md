# 📚 GAONNURI PROJECT
> - 소설, 시, 에세이 등 다양한 분야의 독서를 즐기는 팀원들이 모여 기획한 프로젝트입니다.  
> - 유저의 실제 독후감을 기반으로 책에 대한 접근성을 높이고, 독서 활동을 촉진하는 **유저 중심 커뮤니티 사이트**를 목표로 개발했습니다.

---

## 📝 프로젝트 소개
### 📘 프로젝트명: 가온누리

유저들이 **책에 대한 리뷰**를 작성하고 공유할 수 있는 게시판을 통해 **독서 소비를 촉진**하고,  
**개인의 독서 취향에 맞는 추천 목록**을 제공하는 커뮤니티 사이트입니다.

---

## 📂 관련문서
- [화면 설계안 (피그마)](https://www.figma.com/design/yFBHOXhBbQU1VXc9GmkSgq/%ED%99%94%EB%A9%B4-%EC%84%A4%EA%B3%84?node-id=0-1&t=3GaRwQ9M9rcqdYgo-1)
- [PPT 발표 자료(CANVA)](https://www.canva.com/design/DAGloWvHsP4/8h46aEslUAnyokPI_-G3Qw/edit?utm_content=DAGloWvHsP4&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton)

---

## 🔧 프로젝트 주요기능

| 분류 | 기능 설명 |
|------|-----------|
| **도서 정보 자동 수집** | - 네이버 도서 Open API 연동을 통한 도서 정보 수집 및 DB 저장 |
| **리뷰 기능** | - 도서별 리뷰 작성, 수정, 삭제<br> - 도서 정보(Bookdata)와 연계된 리뷰 관리 |
| **소셜 로그인 기능** | - 네이버, 카카오, 구글 계정 연동 지원 (OAuth2) |
| **회원 인증 및 관리** | - Spring Security 기반 인증 처리 및 사용자 관리 |
| **이메일 발송 기능** | - Spring Mail API 기반 이메일 인증/안내 메일 전송 |
| **이미지 업로드 기능** | - 리뷰 및 도서 이미지 업로드 지원 |

---

## 👥 프로젝트 기여자

| 이름 | 역할 |
|------|------|
| **김경미<br>(팀장)** | **데이터베이스 총괄 관리 및 초기 설계**<br> - MySQL DB 및 테이블 설계, 관계 설정<br> - View 연결 및 도서 정보 스케줄링 기능 구현 |
| **박지윤** | **회원 기능 및 소셜 로그인 담당**<br> - Member DB 및 View 구현<br> - Google/Naver/Kakao 로그인, 계정 탈퇴 등 구현<br> - OpenAI 기반 랜덤 닉네임 생성 기능 구현 |
| **이수정** | **리뷰 시스템 전반 담당**<br> - 리뷰 DB 설계 및 View 구현<br> - 리뷰 작성, 수정, 삭제 등 CRUD 기능 구현<br> - 메인 페이지 전체 리뷰 View 구성 |
| **최예진** | **외부 도서 API 연동 및 도서 정보(Bookdata) 관리**<br> - Naver Book API 연동 구현<br> - 도서 정보 저장 및 View 구성 |

---

## 🛠 사용 기술 스택

| 구분 | 기술 | 버전 | 설명 |
|------|------|------|------|
| **Language** | Java | 11 | 백엔드 애플리케이션 개발 언어 |
| **Framework** | Spring Boot | 2.7.14 | 전체 서비스 구조 구성 및 관리 |
|  | Spring Security | 5.7.x | 인증, 인가 및 보안 처리 |
|  | Spring Data JPA | 내장 | ORM 기반 DB 연동 |
|  | Spring Web (MVC) | 내장 | 웹 요청 처리 및 REST API 구성 |
| **Template Engine** | Thymeleaf | - | HTML 기반 서버 템플릿 렌더링 |
|  | Thymeleaf Layout Dialect | 최신 | 템플릿 레이아웃 구성 보조 |
|  | Thymeleaf Extras SpringSecurity5 | 내장 | 로그인 상태 등 템플릿 제어 |
| **Database** | MySQL | 8.0 | 데이터 저장 및 관리 |
| **Build Tool** | Maven | - | 의존성 및 빌드 관리 |
| **Dev Tool** | Spring Boot DevTools | 내장 | 자동 재시작 등 개발 편의성 향상 |
| **ORM** | Hibernate (JPA 기반) | 내장 | 객체-관계 매핑 |
| **Validation** | Spring Validation | 내장 | 유효성 검사 처리 |
| **Security** | OAuth2 Client | 내장 | 소셜 로그인 연동 (Google, Kakao, Naver) |
| **Email** | Javax Mail | 1.4.7 | 이메일 인증 및 전송 |
| **File Upload** | Commons FileUpload | 1.3.1 | 이미지 업로드 기능 처리 |
| **JSON 처리** | Gson | 2.8.2 | Kakao API 등 JSON 파싱 |
|  | Jackson Databind | 내장 | JSON 직렬화/역직렬화 |
| **AI 연동** | OpenAI Java SDK | 0.11.1 | ChatGPT 3.5 기반 닉네임 추천 |
| **Testing** | Spring Boot Test | 내장 | 단위/통합 테스트 |
|  | Spring Security Test | 내장 | 인증 관련 테스트 지원 |
| **기타** | Lombok | 1.18.28 | 코드 간결화 (어노테이션 기반) |

---

## 🌐 사용한 API

| 항목 | 설명 |
|------|------|
| NaverLogin | 네이버 로그인 연동 |
| NaverBook | 도서 정보 수집 |
| KakaoLogin | 카카오 로그인 연동 |
| GoogleLogin | 구글 로그인 연동 |
| OpenAI GPT-3.5 API | AI 기반 닉네임 추천 기능 |

---

## 🤝 협업 도구

- Git/GitHub  
  버전 관리 및 코드 공유, PR 리뷰 및 브랜치 전략 사용

- Sourcetree  
  Git GUI 툴로 브랜치 시각화 및 커밋 내역 관리

- KakaoTalk  
  빠른 피드백 및 일정 조율을 위한 실시간 커뮤니케이션

- Discord  
  음성 회의 및 화면 공유를 통한 원격 회의 진행

- 오프라인 미팅  
  주요 기획 회의 및 설계 논의는 직접 만나서 진행
