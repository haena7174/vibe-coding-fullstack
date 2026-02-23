# Spring Boot 프로젝트 명세서: VibeApp

이 문서는 최소 기능 스프링부트 애플리케이션인 `vibeapp` 프로젝트의 설정 및 구조를 설명합니다.

## 1. 프로젝트 개요
- **프로젝트 명:** VibeApp
- **설명:** 최소 기능 스프링부트 애플리케이션을 생성하는 프로젝트다.
- **목표:** 최신 기술 스택을 활용한 기본적인 스프링부트 환경 구축.

## 2. 환경 설정
| 항목 | 요구사항 |
| :--- | :--- |
| **JDK** | JDK 25 이상 |
| **언어** | Java |
| **Spring Boot** | 4.0.1 이상 |
| **빌드 도구** | Gradle 9.3.0 이상 |
| **DSL** | Groovy DSL |
| **설정 형식** | YAML (`application.yml`) |

## 3. 프로젝트 메타데이터
- **Group ID:** `com.example`
- **Artifact ID:** `vibeapp`
- **버전:** `0.0.1-SNAPSHOT`
- **메인 클래스:** `com.example.vibeapp.VibeApp`
- **패키지명:** `com.example.vibeapp`

## 4. 빌드 구성 (`build.gradle`)
### 플러그인
- `org.springframework.boot` 버전 `4.0.1`
- `java`

### 의존성
- Gradle 네이티브 플랫폼(BOM) 지원 활용: `org.springframework.boot:spring-boot-dependencies:4.0.1`
- `spring-boot-starter`
- `spring-boot-starter-web`
- `spring-boot-starter-thymeleaf`
- `spring-boot-starter-test` (테스트용)

## 5. 설정 파일 형식
- `.properties` 대신 `src/main/resources/application.yml` 사용.

---
*사용자 요구사항에 따라 생성됨.*
