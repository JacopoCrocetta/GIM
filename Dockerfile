ARG MAVEN_VERSION=3.6.3-openjdk-14
ARG OPENJDK_VERSION=14-jre-hotspot
FROM maven:$MAVEN_VERSION as builder
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline

COPY  src/ /build/src/
RUN mvn package -DskipTests

FROM adoptopenjdk:$OPENJDK_VERSION
WORKDIR /usr/list-service
COPY --from=builder /build/target/shoppinglist*.jar ./shoppinglist.jar
ENTRYPOINT ["java", "-jar", "shoppinglist.jar"]
EXPOSE 8082