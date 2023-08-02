
ARG MICROSERVICES_MOVEMENT=microservice-movement

FROM amazoncorretto:18-alpine as builder

ARG MICROSERVICES_MOVEMENT

WORKDIR /app/$MICROSERVICES_MOVEMENT

COPY ./pom.xml /app
COPY ./$MICROSERVICES_MOVEMENT/.mvn ./.mvn
COPY ./$MICROSERVICES_MOVEMENT/mvnw .
COPY ./$MICROSERVICES_MOVEMENT/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./$MICROSERVICES_MOVEMENT/src ./src

RUN ./mvnw clean package -DskipTests


FROM amazoncorretto:18-alpine as image-app

ARG MICROSERVICES_MOVEMENT

WORKDIR /app/

RUN mkdir ./logs

ARG TARGET_FOLDER=/app/$MICROSERVICES_MOVEMENT/target
ARG PORT_APP=8003


COPY --from=builder $TARGET_FOLDER/microservice-movement-0.0.1-SNAPSHOT.jar .

ENV PORT $PORT_APP

EXPOSE $PORT

CMD sleep 20 && java -jar microservice-movement-0.0.1-SNAPSHOT.jar