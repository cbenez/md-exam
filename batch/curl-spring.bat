@REM se crea un proyecto desde spring-boot con tantas dependencias necesarias como sea posible
curl https://start.spring.io/starter.zip -d javaVersion=11 -d groupId=com.exercise -d artifactId=heros -d name=heros -d description="exercise for heros" -d package=com.exercise.heros -d dependencies=web,devtools,data-jdbc,h2,lombok,flyway,mysql,data-jpa,security -d type=maven-project -d bootVersion=2.7.8 -o heros.zip


@REM  https://start.spring.io/#!type=gradle-project&language=java&platformVersion=2.7.8&packaging=jar&jvmVersion=11&groupId=com.example&artifactId=demo&name=demo&description=Demo%20project%20for%20Spring%20Boot&packageName=com.example.demo

@REM https://start.spring.io/#!type=gradle-project&language=java&platformVersion=2.7.8&packaging=jar&jvmVersion=11&groupId=com.example&artifactId=demo&name=demo&description=Demo%20project%20for%20Spring%20Boot&packageName=com.example.demo&dependencies=lombok,flyway,mysql,data-jpa,security