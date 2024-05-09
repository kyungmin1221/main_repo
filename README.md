![image](https://github.com/kyungmin1221/main_repo/assets/105621255/81933843-43cb-4e1d-80c7-d8a97e65f434)![header](https://capsule-render.vercel.app/api?type=waving&color=6994CDEE&text=&animation=twinkling&height=80)

![썸네일 시안2](https://github.com/kyungmin1221/main_repo/assets/105621255/d89bf6d6-48f9-4403-8a27-79dc7ff208bc)
<br>


</div>

# 🚚 음식배달 서비스 - 푸드리움 🚚
"푸드리움" 은 2주가량의 짧은 프로젝트로 간단한 배달 서비스를 개발한 프로젝트 입니다. 

## 프로젝트 개요
> **🤩 프로젝트 이름 : 푸드리움** <br/>
**📆 개발기간: 2024.03.11 ~ 2024.03.25** <br/>
**🛠️ 언어 : Spring Boot** <br />


## 개발 환경

## Stacks 

### Environment
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white)
![Github](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white)             

### BackEnd FrameWork 
![SpringBoot](https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)   
![Soket](https://img.shields.io/badge/socket.io-010101?style=for-the-badge&logo=socket.io&logoColor=white)   
<img src="https://img.shields.io/badge/JPA-212121?style=  &logo=jpa&logoColor=white"/>
<img src="https://img.shields.io/badge/Querydsl-0285C9?style=  &logo=querydsl&logoColor=white"/>

### DataBase
![MYSQL](https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white) 

### Development Tools
<img src="https://img.shields.io/badge/IntelliJ IDEA-000000?style=flat-square&logo=intellij-idea&logoColor=white">


### Communication
![Slack](https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=Slack&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white)


### GIT 브랜치 전략

- Git-flow 전략을 기반으로 main, develop 브랜치와 feature 보조 브랜치를 운용했습니다.
- main, develop, Feat 브랜치로 나누어 개발을 하였습니다.
    - **main** 브랜치는 배포 단계에서만 사용하는 브랜치입니다.
    - **develop** 브랜치는 개발 단계에서 git-flow의 master 역할을 하는 브랜치입니다.
    - **Feat** 브랜치는 기능 단위로 독립적인 개발 환경을 위하여 사용하고 merge 후 각 브랜치를 삭제해주었습니다.

<br>

## 🛠️ 트러블 슈팅
- 대량의 음식점 조회(10 만 건 ~ 90 만 건) 시 연관관계 N + 1 문제 발생
- 1 ) Store 를 조회할 때, User의 연관관계 fetch 
타입 설정을 따로 하지 않은 상황 @OneToOne(Default : FetchType.EAGER)
- 2 ) 80 만 건 기준 Store Data 조회 시 약 29.38 초 걸림  -> User의 모든 연관관계 데이터 쿼리가 N번 추가적으로 발생(80만 + 1)

### ⭐️ query 문제
- Store 엔티티에, User 엔티티가 @OneToOne 연관관계로 매핑되어 있는데, 
기본 fetch 타입이 즉시로딩(EAGER)이다.
즉시 로딩으로 인해, User 엔티티를 사용하지도 않음에도 불구하고
User 엔티티를 찾는 쿼리가 나가서 N + 1 문제 발생했다.
fetch 타입을 LAZY로 변경하여 이 부분을 해결.

## ⭐️⭐️ 개선 
<img width="455" alt="image" src="https://github.com/kyungmin1221/main_repo/assets/105621255/423c87a8-0757-4c63-867a-5d7dcb25904f">
<img width="481" alt="image" src="https://github.com/kyungmin1221/main_repo/assets/105621255/38dd45b5-2b41-49df-8cdf-ac967d2d6b58">

- Lazy 를 사용함으로 인해 쿼리 개선

## ⭐️⭐️⭐️ 추가 문제 - 해결
- LAZY 로딩이라 하더라도, Store의 연관관계 User 엔티티를 참조하는 순간, 프록시 객체를 초기화하며 select 쿼리가 또 나가며 N + 1 문제가 해결되진 않음
<img width="613" alt="image" src="https://github.com/kyungmin1221/main_repo/assets/105621255/2e033bb5-ec8e-44b1-9484-002a412e11ee">

- ➡️ **Fetch join** 을 통해 필요한 연관관계 데이터를 지연 로딩이 아닌 JOIN 한방 쿼리로 가져오도록 함
- 결과적으로 **Fetch join을 통해 N + 1 문제를 해결**


  



<br>

