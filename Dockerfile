FROM jdk-11.0.15

WORKDIR /app
COPY ./target/github-proxy-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "github-proxy-0.0.1-SNAPSHOT.jar"]