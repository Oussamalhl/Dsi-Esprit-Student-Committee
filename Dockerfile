FROM  maven:3-jdk-8
WORKDIR /Dsi-Esprit-Student-Committee
COPY . .
RUN mvn clean install
EXPOSE 8080

CMD mvn spring-boot:run