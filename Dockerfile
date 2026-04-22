FROM eclipse-temurin:25-jre-alpine
RUN mkdir /opt/app
WORKDIR /opt/app
COPY harmoGestionAPI-0.0.1-SNAPSHOT.jar harmoGestionAPI.jar
EXPOSE 8080
CMD ["java","-jar","harmoGestionAPI.jar"]