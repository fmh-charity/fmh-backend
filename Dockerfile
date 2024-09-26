FROM maven:3-openjdk-17-slim

WORKDIR /usr/src/app

COPY . /usr/src/app
RUN mvn clean package -pl backend -am

ENV PORT 5000
EXPOSE $PORT
CMD [ "sh", "-c", "mvn -Dserver.port=${PORT} -pl backend spring-boot:run" ]
