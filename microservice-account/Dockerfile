
ARG MICROSERVICES_ACCOUNT=microservice-account

FROM amazoncorretto:18-alpine as builder

ARG MICROSERVICES_ACCOUNT

WORKDIR /app/$MICROSERVICES_ACCOUNT

COPY ./pom.xml /app
COPY ./$MICROSERVICES_ACCOUNT/.mvn ./.mvn
COPY ./$MICROSERVICES_ACCOUNT/mvnw .
COPY ./$MICROSERVICES_ACCOUNT/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./$MICROSERVICES_ACCOUNT/src ./src

RUN ./mvnw clean package -DskipTests


FROM amazoncorretto:18-alpine as image-app

ARG MICROSERVICES_ACCOUNT

WORKDIR /app/

RUN mkdir ./logs

ARG TARGET_FOLDER=/app/$MICROSERVICES_ACCOUNT/target
ARG PORT_APP=8002


COPY --from=builder $TARGET_FOLDER/microservice-account-0.0.1-SNAPSHOT.jar .

ENV PORT $PORT_APP

EXPOSE $PORT

CMD sleep 20 && java -jar microservice-account-0.0.1-SNAPSHOT.jar