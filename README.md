<p align="center">
    <img width="200px;" src="https://raw.githubusercontent.com/woowacourse/atdd-subway-admin-frontend/master/images/main_logo.png"/>
</p>
<p align="center">
  <img alt="npm" src="https://img.shields.io/badge/npm-6.14.15-blue">
  <img alt="node" src="https://img.shields.io/badge/node-14.18.2-blue">
  <a href="https://edu.nextstep.camp/c/R89PYi5H" alt="nextstep atdd">
    <img alt="Website" src="https://img.shields.io/website?url=https%3A%2F%2Fedu.nextstep.camp%2Fc%2FR89PYi5H">
  </a>
  <img alt="GitHub" src="https://img.shields.io/github/license/next-step/atdd-subway-admin">
</p>

<br>

# 지하철 노선도 미션

[ATDD 강의](https://edu.nextstep.camp/c/R89PYi5H) 실습을 위한 지하철 노선도 애플리케이션

<br>


## 배운점
- ATDD에 대해 학습
  - OutSide-In: 외부에서 부터 내부로 진행
  - InSide-Out: 도메인부터 외부 레이어로 진행
- 인수 테스트 기반 리팩터링
  - 서비스레이어의 비즈니스로직을 도메인으로 옮기기
  - 인수 테스트로부터 보호를 받으며 세부 기능들을 TDD 사이클로 리팩터링 하기
  
## 주요 피드백
- 원시객체로 포장하고 객체로써의 역할과 책임을 한번 부여해보자
- 도메인에 대한 검증이나 비즈니스 플로우 등이 겹칠 텐데 이에 대한 변경 사항 대응도 여기저기 흩어져 있다면 대응하기가 어렵다
- 매직넘버들이 추출된다면 훨씬 혼동을 줄일 수 있다.
- RuntimeException은 너무 포괄적이다. 비즈니스 요건에 맞는 Exception을 정의해보자


## 🚀 Getting Started

### Install

#### npm 설치

```
cd frontend
npm install
```

> `frontend` 디렉토리에서 수행해야 합니다.

### Usage

#### webpack server 구동

```
npm run dev
```

#### application 구동

```
./gradlew bootRun
```

<br>

## ✏️ Code Review Process

[텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

<br>

## 🐞 Bug Report

버그를 발견한다면, [Issues](https://github.com/next-step/atdd-subway-service/issues) 에 등록해주세요 :)

<br>

# 요구사항 정리

## 1단계

- [x] LineService 리팩터링
    - [x] 도메인으로 옮길 로직 찾기
    - [x] 도메인 단위테스트 작성
    - [x] 도메인으로 로직이동
- [x] (선택) LineSectionAcceptanceTest 리팩터링

## 2단계

- [x] 최단 경로 조회 인수테스트 만들기
- [x] 최단 경로 조회 기능 구현하기

## 3단계

- [x] 토큰 발급 기능 (로그인) 인수 테스트 만들기
- [x] 인증 - 내 정보 조회 기능 완성하기
- [x] 인증 - 즐겨 찾기 기능 완성하기

## 4단계

- [ ] 경로 조회 시 거리 기준 요금 정보 포함하기
- [ ] 노선별 추가 요금 정책 추가
- [ ] 연령별 할인 정책 추가

## 📝 License

This project is [MIT](https://github.com/next-step/atdd-subway-service/blob/master/LICENSE.md) licensed.
