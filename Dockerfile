FROM openjdk:17-alpine

EXPOSE 8080

COPY cards/cards.json cards/cards.json

ADD target/moneyTransferProj-0.0.1-SNAPSHOT.jar moneyTransferApp.jar

CMD ["java", "-jar", "moneyTransferApp.jar"]