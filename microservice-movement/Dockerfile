
ARG MICROSERVICES_MOVEMENT=microservice-movement
ARG COMMON_MODULE=common

FROM amazoncorretto:18-alpine as builder-microservice-movement

ARG MICROSERVICES_MOVEMENT

WORKDIR /app/$MICROSERVICES_MOVEMENT

COPY ./pom.xml /app
COPY ./$MICROSERVICES_MOVEMENT/.mvn ./.mvn
COPY ./$MICROSERVICES_MOVEMENT/mvnw .
COPY ./$MICROSERVICES_MOVEMENT/pom.xml .

RUN sed -i 's/\r$//' mvnw

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./$MICROSERVICES_MOVEMENT/src ./src

RUN ./mvnw clean package -DskipTests


FROM amazoncorretto:18-alpine as builder-common-module

ARG COMMON_MODULE

WORKDIR /app/$COMMON_MODULE

COPY ./pom.xml /app
COPY ./$COMMON_MODULE/.mvn ./.mvn
COPY ./$COMMON_MODULE/mvnw .
COPY ./$COMMON_MODULE/pom.xml .

RUN sed -i 's/\r$//' mvnw

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./$COMMON_MODULE/src ./src

RUN ./mvnw clean package -DskipTests



FROM amazoncorretto:18-alpine as image-app

ARG MICROSERVICES_MOVEMENT
ARG COMMON_MODULE

WORKDIR /app/

RUN mkdir ./logs

ARG TARGET_FOLDER=/app/$MICROSERVICES_MOVEMENT/target
ARG TARGET_FOLDER_COMMON=/app/$COMMON_MODULE/target
ARG PORT_APP=8003


COPY --from=builder-microservice-movement $TARGET_FOLDER/microservice-movement-1.0-SNAPSHOT.jar .
COPY --from=builder-common-module $TARGET_FOLDER_COMMON/common-1.0-SNAPSHOT.jar .

ENV PORT $PORT_APP

EXPOSE $PORT

CMD java -jar common-1.0-SNAPSHOT.jar
CMD sleep 20 && java -jar microservice-movement-1.0-SNAPSHOT.jaroservice-movement-1.0-SNAPSHOT.jarr