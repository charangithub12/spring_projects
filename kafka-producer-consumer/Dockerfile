FROM openjdk:17
EXPOSE 5006
ADD target/springboot-docker.jar /springboot-docker.jar
COPY /kafka-client-truststore.jks /kafka-client-truststore.jks
COPY /kafka-client-keystore.jks /kafka-client-keystore.jks
ENTRYPOINT ["java","-jar","/springboot-docker.jar"]