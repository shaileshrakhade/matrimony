#step 2 dwonlode jre & unzip project
#jre to run program
FROM eclipse-temurin:17.0.4.1_1-jre as builder
WORKDIR extracted
ADD target/*.jar app.jar
#extract meanse it's not directly run the jar it unzip it then run
RUN java -Djarmode=layertools -jar app.jar extract

#step 2 copy data from extracted to application folder in docker container
FROM eclipse-temurin:17.0.4.1_1-jre
WORKDIR application
COPY --from=builder extracted/dependencies/ ./
COPY --from=builder extracted/spring-boot-loader/ ./
COPY --from=builder extracted/snapshot-dependencies/ ./
COPY --from=builder extracted/application/ ./
#use the port to run apllication on 8080
EXPOSE 8080
#cmd to run java program
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]


#mvn clean compile jib:build