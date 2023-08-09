
ARG MICROSERVICES_CLIENT=microservice-client

FROM amazoncorretto:18-alpine as builder

ARG MICROSERVICES_CLIENT

WORKDIR /app/$MICROSERVICES_CLIENT

COPY ./pom.xml /app
COPY ./$MICROSERVICES_CLIENT/.mvn ./.mvn
COPY ./$MICROSERVICES_CLIENT/mvnw .
COPY ./$MICROSERVICES_CLIENT/pom.xml .

#RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./$MICROSERVICES_CLIENT/src ./src

RUN ./mvnw clean package


FROM amazoncorretto:18-alpine as image-app

ARG MICROSERVICES_CLIENT

WORKDIR /app/

RUN mkdir ./logs

ARG TARGET_FOLDER=/app/$MICROSERVICES_CLIENT/target
ARG PORT_APP=8001


COPY --from=builder $TARGET_FOLDER/microservice-client-1.0-SNAPSHOT.jar .

ENV PORT $PORT_APP

EXPOSE $PORT

CMD sleep 20 && java -jar microservice-client-1.0-SNAPSHOT.jar