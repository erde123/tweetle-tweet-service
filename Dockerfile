FROM gradle:8.8-jdk21

WORKDIR /opt/app

EXPOSE 8002

COPY build/libs/tweetle-tweet-service-0.0.1-SNAPSHOT.jar ./
#
ENV SPRING_PROFILES_ACTIVE=docker

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar tweetle-tweet-service-0.0.1-SNAPSHOT.jar"]