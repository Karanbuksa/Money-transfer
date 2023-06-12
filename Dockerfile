FROM openjdk:17-alpine

EXPOSE 8080

ADD target/moneyTransferProj-0.0.1-SNAPSHOT.jar moneyTransferApp.jar

CMD ["java", "-jar", "moneyTransferApp.jar"]