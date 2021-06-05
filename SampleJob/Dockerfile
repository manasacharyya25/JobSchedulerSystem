FROM maven:3.6.0-jdk-11-slim AS build
COPY src /usr/src/app/src  
COPY pom.xml /usr/src/app  
RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests

FROM openjdk:11-jre-slim
COPY --from=build /usr/src/app/target/SampleJobApplication-0.0.1-SNAPSHOT.jar /usr/app/SampleJobApplication-0.0.1-SNAPSHOT.jar
EXPOSE 8085  
ENTRYPOINT ["java","-jar","/usr/app/SampleJobApplication-0.0.1-SNAPSHOT.jar"] 