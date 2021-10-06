<h1 align="center">Welcome to K.Cook-Server 👋</h1>
<p>
  <img alt="Version" src="https://img.shields.io/badge/version-1.0.0-blue.svg?cacheSeconds=2592000" />
  <a href="https://github.com/kefranabg/readme-md-generator/graphs/commit-activity" target="_blank">
    <img alt="Maintenance" src="https://img.shields.io/badge/Maintained%3F-yes-green.svg" />
  </a>
  <a href="https://github.com/vividswan/K.Cook-Server/blob/main/LICENSE" target="_blank">
    <img alt="License: MIT" src="https://img.shields.io/github/license/vividswan/K.Cook-Server" />
  </a>
</p>

> K.Cook Back-End Server Project

<div align=center>
  <img width="249" alt="케이쿡마크" src="https://user-images.githubusercontent.com/54254402/133126938-f76c8dfb-255e-4daf-afef-499736d987f6.png">
</div>
<h2 align=center>케이쿡은 커스텀케이크 주문 플랫폼을 개발하고 있는 초기스타트업입니다.</h2>

![웹 1920 – 케이크](https://user-images.githubusercontent.com/54254402/133126499-abab1a68-12dd-429c-b204-5be48b3b11a4.png)


## API Docs

### ✨ [K.Cook Server Swagger](https://prod.kcook-cake.com/swagger-ui/index.html)

## 기술스택

<p>
  <img src="https://img.shields.io/badge/-SpringBoot-blue"/>&nbsp
  <img src="https://img.shields.io/badge/-JPA-red"/>&nbsp
  <img src="https://img.shields.io/badge/-MySQL-yellow"/>&nbsp
  <img src="https://img.shields.io/badge/-JWT-blue"/>&nbsp
  <img src="https://img.shields.io/badge/-AWS-orange"/>&nbsp
  <img src="https://img.shields.io/badge/-Nginx-red"/>&nbsp
  <img src="https://img.shields.io/badge/-Swagger-black"/>&nbsp
  <img src="https://img.shields.io/badge/-SpringSecurity-green"/>&nbsp
  <img src="https://img.shields.io/badge/-Querydsl-violet"/>&nbsp
</p>

## 개발환경


- backend
  - java11
  - gradle
  - spring-boot 2.5.3

## 시스템 구성도

![drawio](https://user-images.githubusercontent.com/54254402/136225436-8693e719-f8cc-4670-bbbd-33e6e68096cc.png)


## Usage

```sh
$ ./gradlew clean build
```

## ERD

<img width="759" alt="스크린샷 2021-10-06 오후 11 36 17" src="https://user-images.githubusercontent.com/54254402/136225484-8fc163fa-2129-4975-b813-f2c25af7a854.png">


### Access server side using following URL

```
https://prod.kcook-cake.com/app
```

## 개발일지

- 회원 가입 API 완성 (21/08/17) - `commit` : [e963d38](https://github.com/vividswan/K.Cook-Server/commit/e963d38c186761ecd3af5c638924f9fd42cbf64b)
- 로그인 API 완성 (21/08/18) - `commit` : [3b4891c](https://github.com/vividswan/K.Cook-Server/commit/3b4891c36db93b0a990759bfb405111ed56b117c)
- 로그인한 회원 정보 조회 API 완성 (21/08/18) - `commit` : [04f78b9](https://github.com/vividswan/K.Cook-Server/commit/04f78b97d081e634cf1a9d764e64e0c5315186ff)
- 사용자 SMS 토큰 생성 및 인증 API 완성 (21/08/19) - `commit` : [ffaf516](https://github.com/vividswan/K.Cook-Server/commit/ffaf51668b2c33c4b0ca7154194dcc40c130cd30)
- 케이크 상품 목록 조회 API, 추가 상품 목록 조회 API 완성 (21/08/24) - `commit` : [9510921](https://github.com/vividswan/K.Cook-Server/commit/9510921e1ff3b74dc7eb16cc305ccf2bbc133f43)
- 상품 디테일 조회 API 완성 (21/08/28) - `commit` : [2b4197a](https://github.com/vividswan/K.Cook-Server/commit/2b4197a744b76a5658d6349dcd089360d88ba12f)
- 생일인 회원 조회 후 쿠폰 증정 기능 완성 `[Scheduler 사용]` (21/08/28) - `commit` : [0b4ea9c](https://github.com/vividswan/K.Cook-Server/commit/0b4ea9ccc9461caf728878212483aa2226d8d087)
- 케이크, 추가 상품 검색 조건 고도화 [`Querydsl`을 활용한 정렬과 여러 인자들의 동적 쿼리 생성] (21/08/30) - `commit` : [dc3cf97](https://github.com/vividswan/K.Cook-Server/commit/dc3cf9736c4dd99759562ea4c6aba59efada77b1), [556bd7a](https://github.com/vividswan/K.Cook-Server/commit/556bd7ac289b97a897fd93b9929cfbf07bcaa60a)
- 주문 생성 API 완성 (21/08/31) - `commit` : [1ef32a8](https://github.com/vividswan/K.Cook-Server/commit/1ef32a839021935b83e87d7d62132c6b78355402)
- 고객 주문 조회 API 완성 (21/09/01) - `commit` : [ff91b18](https://github.com/vividswan/K.Cook-Server/commit/ff91b1891dcadb84616f31711deb83668f6e0614)
- 회원가입, 로그인 통합 테스트 코드 (21/09/07) - `commit` : [b9a0815](https://github.com/vividswan/K.Cook-Server/commit/b9a0815d17ad3133c8387d669c2dc70dc3ea5314), [6279b75](https://github.com/vividswan/K.Cook-Server/commit/6279b75740b2b60629f2e290fdaaef62b68b04c9), `수정 commit` : [7c4bf18](https://github.com/vividswan/K.Cook-Server/commit/7c4bf1873b1e7ac6d40bc62a8bb43db269f74a1f)
- 인증 된 계정의 스토어 조회 API 완성 (21/09/08) - `commit` : [d30b71d](https://github.com/vividswan/K.Cook-Server/commit/d30b71d68fc6b72fb4266fba9ab6b4266c9b995b)
- 관리자 권한으로 회원 Role 변경 API 완성 (21/09/08) - `commit` : [fb8f56f](https://github.com/vividswan/K.Cook-Server/commit/fb8f56f572d0dd034c856218097525deef910f04)
- Store 생성, 수정 API 완성 (21/09/09) - `commit` : [8da80bc](https://github.com/vividswan/K.Cook-Server/commit/8da80bce7c556c4101fb398d67f172605c0f8abf), [a24bc2b](https://github.com/vividswan/K.Cook-Server/commit/a24bc2b2b82d50f0be0ec6e1803b983b6bc452f8)
- Product Detail 조회 API 통합테스트 코드 작성 (21/09/10) - `commit` : [4400cc6](https://github.com/vividswan/K.Cook-Server/commit/4400cc651818cd04363efad146a52a8a591b4fca)
- Product 생성 API 완성 (21/09/13) - `commit` : [4477731](https://github.com/vividswan/K.Cook-Server/commit/4477731f2345bef774df05783932552c1d577ef5), [d518792](https://github.com/vividswan/K.Cook-Server/commit/d518792f5dd720a3b51253c78e66d9ba5d102ccb), [3c2243e](https://github.com/vividswan/K.Cook-Server/commit/3c2243eaa1d8ab2cb5c9030442350f125bca51d1)
- 백엔드 빌드 및 배포 자동화(잰킨스, 도커) (21/09/21) : [시스템 구상도](https://user-images.githubusercontent.com/54254402/134108670-568b2458-b80b-4706-a27b-c027e7aa7d1e.png)
- 계정의 쿠폰 조회 API 완성 (21/09/30) - `commit` : [1492cb9](https://github.com/vividswan/K.Cook-Server/commit/1492cb91a933c76e5c7448623b6b6ddceb1fa5dc)
- 로그인 한 회원의 사용 API 로그 저장 기능 추가(`Spring AOP`) (21/10/06) - `commit` : [2b387bc](https://github.com/vividswan/K.Cook-Server/commit/2b387bc42bcea2982078ff9a3ae2b54a7913537b)



## Author

👤 **vividswan**

* Website: vividswan.github.io
* Github: [@vividswan](https://github.com/vividswan)

## 🤝 Contributing

Contributions, issues and feature requests are welcome!<br />Feel free to check [issues page](https://github.com/vividswan/K.Cook-Server/issues). 

## Show your support

Give a ⭐️ if this project helped you!

## 📝 License

Copyright © 2021 [vividswan](https://github.com/vividswan).<br />
This project is [MIT](https://github.com/vividswan/K.Cook-Server/blob/main/LICENSE) licensed.

***
_This README was generated with ❤️ by [readme-md-generator](https://github.com/kefranabg/readme-md-generator)_
