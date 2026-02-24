# Spring Boot 프로젝트 명세서: VibeApp

이 문서는 최소 기능 스프링부트 애플리케이션인 `vibeapp` 프로젝트의 설정 및 구조를 설명함과 동시에 현재 구현 상태를 기록합니다.

## 1. 프로젝트 개요
- **프로젝트 명:** VibeApp
- **설명:** 게시판 기능을 제공하는 pure REST API 서버 프로젝트다.
- **목표:** 최신 기술 스택을 활용한 RESTful API 구축 및 OpenAPI를 통한 문서화.

## 2. 환경 설정 (확정)
| 항목 | 요구사항 | 현재 상태 |
| :--- | :--- | :--- |
| **JDK** | JDK 25 이상 | JDK 25 (Java 25) |
| **언어** | Java | Java |
| **Spring Boot** | 4.0.1 이상 | 4.0.1 |
| **빌드 도구** | Gradle 9.3.0 이상 | Gradle 기반 (build.gradle) |
| **DSL** | Groovy DSL | Groovy DSL 적용 |
| **설정 형식** | YAML (`application.yml`) | `application.yml` 사용 (Port: 8081) |

## 3. 프로젝트 메타데이터
- **Group ID:** `com.example`
- **Artifact ID:** `vibeapp`
- **버전:** `0.0.1-SNAPSHOT`
- **메인 클래스:** `com.example.vibeapp.VibeApp`
- **패키지 구조:** 기능 기반 구조 (Feature-based Package Structure)
    - `com.example.vibeapp.post`: 게시글 도메인, 서비스, 리포지토리, 컨트롤러 (REST API)

## 4. 빌드 구성 (`build.gradle`)
### 플러그인
- `org.springframework.boot` 버전 `4.0.1`
- `java`

### 핵심 의존성
- `spring-boot-starter-web` (REST API 전용)
- `spring-boot-starter-validation` (Bean Validation)
- `spring-boot-starter-test` (테스트용)

## 5. 아키텍처 및 설계 원칙
    - 자바: `src/main/java/com/example/vibeapp/{feature}/`
- **DTO 패턴 적용**: 엔티티를 직접 노출하지 않고 DTO(Data Transfer Object)를 통해 데이터를 전달.
    - 정적 팩토리 메서드 `from()`을 통한 엔티티 → DTO 변환.
    - DTO 내부 `toEntity()` 또는 `updateEntity()`를 통한 DTO → 엔티티 변환/업데이트.
- **Bean Validation**: 입력 데이터 검증을 위해 JSR-380 어노테이션(`@NotBlank`, `@Size` 등) 적용.
- **리포지토리**: 현재 인메모리(Memory) 기반 리포지토리(`PostRepository`)를 사용하여 초기 데이터 시딩(10개) 및 CRUD 수행.
- **명명 규칙**: 컨트롤러와 서비스 간 일관된 메서드 명칭 사용 (`listPosts`, `viewPost`, `createPost`, `updatePost`, `deletePost`).

## 6. 주요 기능 현황 (업데이트됨)
- **[구현 완료] 게시글 관리 (Post CRUD)**:
    - 목록 보기 (`/api/posts`)
    - 상세 조회 (`/api/posts/{id}`)
    - 새 글 작성 (`/api/posts`, POST)
    - 수정 (`/api/posts/{id}`, PATCH)
    - 삭제 (`/api/posts/{id}`, DELETE)
- **[구현 완료] 페이징 처리**:
    - 게시글 목록 하단에 페이지 내비게이션 포함.
    - 페이지당 5개씩 출력, 현재 10개의 테스트 데이터 로드됨.
- **[구현 완료] 반응형 UI**:
    - **디자인 시스템**: Tailwind CSS 및 Google Fonts (Space Grotesk) 적용.
    - **스타일**: 유리공예(Glassmorphism) 및 파스텔 톤 가미된 모던 스타일.

## 7. 설정 파일
- `src/main/resources/application.yml` 파일을 통해 포트 및 앱 이름 관리.

---
*최종 업데이트: 2026-02-24*
*현재 프로젝트는 핵심 기능 구현 및 디자인 리팩토링이 완료된 상태입니다.*
