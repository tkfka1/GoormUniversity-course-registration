#  course-registration-GoormUniversity-restapi
 
 
 main.java.resources
 application-aws.yaml 생성 후
 <code>
 spring:
  datasource:
    url: jdbc:mysql://{mysql주소}/{데이터베이스}?useSSL=false&useUnicode=true&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true
    username: {user}
    password: {password}
    driver-class-name: com.mysql.cj.jdbc.Driver
   </code>

주소, 데이터베이스, 유저, 패스워드 작성
