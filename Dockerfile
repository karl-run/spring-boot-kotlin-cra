FROM openjdk:8-jre-alpine

COPY ./server/build/libs/server-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8080

CMD ["java", "-jar", "server-0.0.1-SNAPSHOT.jar"]
