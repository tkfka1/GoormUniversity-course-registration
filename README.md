# 구름대학 수강신청

## 1. 프로젝트 개요

학생들이 수강신청 기간에 각 학기마다 수강할 강의를 선택하고 등록할 수 있는 온라인 서비스. 학생들은 이를이용하여 강의 검색, 수강 신청, 강의 일정 확인 등을 한다.

안정적인 인프라를 구축하고, 대규모 트래픽을 처리할 수 있는 서비스를 구현한다.

## 2.팀 구성및 역할 및 협업 방식

### 2-1 팀 구성 및 역할

| 이름 | 역할 | 담당 업무 |
| --- | --- | --- |
| 정한교 | 팀장 | 서비스 구현 관련 전반적인 개발, 전체적인 설계및 기획, 팀원 조율,문서 제작, 영상제작 |
| 신현식 | 팀원 | 인프라 구축, CICD 파이프라인 설계 및 구축 |
| 최수환 | 팀원 | 인프라구축, CICD 파이프라인 설계 및 구축, 배포용 깃 관리 |
| 이승엽 | 팀원 | 인프라 구축, monitoring 환경 구축, PPT 발표 |
| 배수빈 | 팀원 | 인프라 구축, monitoring  |

### 2-2 협업 방식

ZOOM 을 통한 화상 회의와 (평일 오전 10시 ~ 오후 7시)

Slack 을 통한 채팅 및 자료 공유

Notion 을 통한 간단한 자료 모음

Google Docs 를 통한 문서 작업

## 3. 진행프로세스

### 1. Phase 0 (03.13 ~ 03.17 5일)
자료 수집 및 기획 단계

● 자료 수집 및 인원 분배

● 각자 필요한 환경 및 기획 작성 (service, infra , cicd, monitor)

● service 기획에 맞게 infra 요구서 작성

● infra 요구서에 맞게 infra 기획서 작성

● 내용을 전부 모아서 프로젝트 착수 보고서 작성

### 2. Phase 1 (03.20 ~ 03.28 8일)

기본적인 테스트 서비스 구현 및 기본적인 인프라 구현으로 테스트 서비스를 가동 단계
Phase 0 에서 진행한 기획에 맞게 기본적인 기능이 동작할 수 있는지에 대한 확인 작업

● infra 요구에 맞는 infrastructure 구성

● sevice test code 빌드 및 배포 (test frontend, test backent) 테스트

● infra 관련 network , authorization, storage 정리

● infra 관련 HA(High Availability) , security 구현

### 3. Phase 2 (03.29 ~ 04.11 10일)

기획에 따른 서비스 기능 개발 및 인프라 CI/CD , Monitoring 구현 및 보고서 작성 마무리 단계

● service 구현(backend api, frontend funtion)

● ci/cd pipeline 구현 (코드관련 jenkins, eks관련 argoCD)

● monitoring service 구현

● 작업 프로젝트 기술 문서, 발표PPT, 영상 작업

## 4. 프로젝트 구현 간단 설명

### 4-1 DB

**MySQL** 로 진행하였고 기획에 맞는 여러 테이블및 컬럼을 작성

● admin (교직원)

● user (학생)

● major (전공)

● professor (교수)

● lecture (강의)

● cart_lecture (장바구니)

● take_lecture (수강신청)

● lecture_class (강의분반)

● open_time (수강신청오픈시간)


### 4-2 Backend

앞서 Usecase와 DB의 정보를 CRUD하는 **REST API**를 **Spring**으로 구현

[https://github.com/tkfka1/course-registration-GoormUniversity-restapi](https://github.com/tkfka1/course-registration-GoormUniversity-restapi)

또한 **MSA (MicroService Architecture)**를 구현해보고자 하여 간단한 **AI 서비스**를 제공하는 백엔드

**pytorch** 를 사용해 연산을 하고 **Flask** **프레임워크**를 사용해 **REST API** 구현 

[https://github.com/tkfka1/course-registration-GoormUniversity-animalapi](https://github.com/tkfka1/course-registration-GoormUniversity-animalapi)

### 4-3 Frontend

앞서 제작한 **Backend**의 **API**와 통신을하여 서비스의 기능을 **Vue.js 프레임워크**를 사용하여

사용자에게 실제로 보여주는 역할Admin과 User가 구분되어 개발을 진행

[https://github.com/tkfka1/course-registration-GoormUniversity-admin](https://github.com/tkfka1/course-registration-GoormUniversity-admin)

[https://github.com/tkfka1/course-registration-GoormUniversity-user](https://github.com/tkfka1/course-registration-GoormUniversity-user)

### 4-4 Infra

AWS EKS 를 사용해 클러스터를 구현하여 고가용성 확보 및 여러 AWS 어플리케이션 이용 (RDS, Route53, EFS storage)

### 4-5 CICD

개발자 → github → Jenkins → dockerhub → github → argoCD → AWS EKS 에 최종적으로 배포

Jenkins 를 이용하여 개발용git 변동사항을 체크하고 변동사항 있을 시 Spring과 node.js(Vue) 빌드 후 도커라이징

argoCD로 배포용git 변동사항 체크하고 변동사항 있을 시 EKS에 배포 

### 4-6 Monitoring

Prometheus & Grafana 를 사용하여 쿠버네티스 클러스터 안의 많은 리소스들을 모니터링 환경 구현

Thanos와 MINIO를 사용하여 프로메테우스 고가용성 구현

AWS CloudWatch 를 사용하여 AWS의 여러 리소스들(EKS클러스터, EC2, RDS, 네트워크 로드밸런서 등) 의모니터링 시각화구현

## 5. 자체평가 및 보완

***전체적인 서비스가 무거움***

이유중 가장 큰 것은 개선이 필요할 것이다.

그다음 이유로는 **DB의 최적화가 되어있지 않아서**이다.

DB의 구조 자체도 개선 가능성이 있지만 더 중요한것은 백엔드단에서 API를 받아오는 행위에서 많은 데이터베이스의 조회가 발생하는데 시간관계상 N+1 문제가 나는 코드를 억지로 고쳐서 사용을 했기에 이것을 개선하면 API의 속도가 많이 개선되어 서비스의 속도를 많이 개선할 수 있을 것이다.

***서비스 Page 디자인 개선***

목표했던 과제인 인프라와 서비스를 구현하였지만 실 **사용자의 경험을 개선**하는것은 어떠한 서비스든 꼭 필요한 부분이라고 할 수 있다. 추후 **디자이너와의 협업**을 통하여 서비스페이지 디자인을 **사용자 경험에 맞추어 개선**을 할 수 있을것이다.

***Machine Learning 서버 파이프라인 구축***

Machine Learning 서버를 위한 Deployment도 EKS 클러스터에서 배포를 하였다. 하지만 시간 관계상 해당 서버를 배포하기 위한 **빌드 테스트 , 도커라이징만을 진행하여 이미지 생성**만 했기 때문에 파이프라인을 구축하지 못하였다. Machine Learning 서버도 파이프라인으로 구축함에 따라 **추가 개발 및 유지보수 , 버전 관리**를 용이하게 할 수 있을 것이다.

***AWS CloudWatch 지표 추적 및 갱신 자동화 고안***

현재 EKS 클러스터 노드들은 수요에 따라 자동으로 증가하거나 감소되는 상황이다. 그러나 AWS CloudWatch에서 추적 대상들 중, 추적 대상을 클러스터 노드로 잡아놓은 위젯들은 추적 대상인 노드가 종료되게 되면 추적 대상을 잃고 값을 표시하지 않게 되는데, 이때 다시 추적할 대상을 수동으로 일일이 지정해 주어야 하는 문제가 있다.

따라서 기존에 추적 중인 노드가 종료되고, 새 노드가 다시 생성되었을 때, 추적할 대상을 잃은 위젯들이 새 노드의 값을 자동으로 다시 추적하여 위젯으로 표시하도록 하는 방법을 고안할 필요가 있다.

***더욱 고도화된 Prometheus HA 구현***

Prometheus HA를 구현하기 위해 Thanos를 이용했다. Thanos sidecar를 구현하고 Prometheus와 Thanos를 성공적으로 연결하여  Prometheus 서비스들을 돌아가면서 사용하는 것 까지는 확인을 했다. 하지만 중복 메트릭 제거등 Thanos의 기능을 완전히 이용하여 완성도있는 고가용성을 구현하지 못하였다. 해당부분을 보완한다면 많은 양의 데이터를 모니터링해야하는 상황에서 더욱 완성도 있는 모니터링 고가용성을 갖출 수 있을 것이다.


## 발표 프레젠테이션

[구름대학교 수강신청 프레젠테이션.pdf](https://github.com/tkfka1/GoormUniversity-course-registration/files/11269406/default.pdf)

## 기술문서

[구름대학 수강신청 프로젝트 기술문서.pdf](https://github.com/tkfka1/GoormUniversity-course-registration/files/11269403/default.pdf)

## 시연영상

전체 영상

[https://youtu.be/8zpsD8fZ_TY](https://youtu.be/8zpsD8fZ_TY)

교직원 서비스

[https://youtu.be/6bbDbWCxg5E](https://youtu.be/6bbDbWCxg5E)

학생서비스

[https://youtu.be/6-AsQjCUzac](https://youtu.be/6-AsQjCUzac)

모니터링 Prometheus & Grafana

[https://youtu.be/Qh6-R9yKtwg](https://youtu.be/Qh6-R9yKtwg)

모니터링 CloudWatch
