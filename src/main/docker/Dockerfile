FROM openjdk:8-jdk-slim
ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
VOLUME /tmp
ADD pathfinder-0.0.1-SNAPSHOT.war pathfinder.war
RUN bash -c 'touch /pathfinder.war'
ENTRYPOINT ["java","-jar", "/pathfinder.war"]