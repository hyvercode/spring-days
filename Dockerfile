FROM openjdk:17-slim
ARG JAR_FILE=target/*.jar

RUN useradd --system -m spring
USER spring:spring

ENV APP_HOME=/home/spring/bin
RUN mkdir $APP_HOME
WORKDIR $APP_HOME

COPY ${JAR_FILE} $APP_HOME/app.jar

ENTRYPOINT exec java $JAVA_OPTS -jar $APP_HOME/app.jar