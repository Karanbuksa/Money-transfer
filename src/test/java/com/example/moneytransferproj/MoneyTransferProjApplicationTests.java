package com.example.moneytransferproj;

import com.example.moneytransferproj.dto.Amount;
import com.example.moneytransferproj.dto.ConfirmOperation;
import com.example.moneytransferproj.dto.TransferData;
import com.example.moneytransferproj.exceptions.ExceptionResponse;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneyTransferProjApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Container
    private static final GenericContainer<?> moneyTransferAppContainer = new GenericContainer<>("money-transfer-app")
            .withExposedPorts(8080);

    private final Integer appPort = moneyTransferAppContainer.getMappedPort(8080);

    @Test
    void test_validTransferData_validConfirmData() {
        //Arrange
        TransferData transferData = new TransferData("1111222233334444", "03/24",
                "789", "8888999900001111", new Amount(4000, "RUR"));

        ConfirmOperation confirmOperation = new ConfirmOperation("0000");
        //Act
        ResponseEntity<String> entityFromApp1 = restTemplate.postForEntity("http://localhost:" + appPort + "/transfer", transferData, String.class);
        ResponseEntity<String> entityFromApp2 = restTemplate.postForEntity("http://localhost:" + appPort + "/confirmOperation", confirmOperation, String.class);
        //Assert
        Assertions.assertEquals(HttpStatusCode.valueOf(200), entityFromApp1.getStatusCode());
        assertThat(entityFromApp1.getBody(), is(notNullValue()));
        assertThat(Integer.parseInt(entityFromApp1.getBody()), notNullValue());
        Assertions.assertEquals(HttpStatusCode.valueOf(200), entityFromApp2.getStatusCode());
        assertThat(entityFromApp2.getBody(), is(notNullValue()));
        assertThat((Integer.parseInt(entityFromApp2.getBody())), notNullValue());
    }

    @SneakyThrows
    @Test
    void test_validTransferData_invalidConfirmData() {
        //Arrange
        TransferData transferData = new TransferData("1111222233334444", "03/24",
                "789", "8888999900001111", new Amount(4000, "RUR"));

        ConfirmOperation confirmOperation = new ConfirmOperation(null);
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.constructType(ExceptionResponse.class);
        //Act
        ResponseEntity<String> entityFromApp1 = restTemplate.postForEntity("http://localhost:" + appPort + "/transfer", transferData, String.class);
        ResponseEntity<String> entityFromApp2 = restTemplate.postForEntity("http://localhost:" + appPort + "/confirmOperation", confirmOperation, String.class);
        //Assert
        Assertions.assertEquals(entityFromApp1.getStatusCode(), HttpStatusCode.valueOf(200));
        assertThat(entityFromApp1.getBody(), is(notNullValue()));
        assertThat(Integer.parseInt(entityFromApp1.getBody()), notNullValue());
        Assertions.assertEquals(HttpStatusCode.valueOf(500), entityFromApp2.getStatusCode());
        ExceptionResponse exceptionResponse = objectMapper.readValue(entityFromApp2.getBody(), javaType);
        assertThat(exceptionResponse.getMessage(), is("Неверный код"));
    }

    @SneakyThrows
    @Test
    void transferEndpointTest_invalidTransferData_unavailableCard() {
        //Arrange
        TransferData transferData = new TransferData("1111222233334443", "03/24",
                "789", "8888999900001111", new Amount(4000, "RUR"));

        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.constructType(ExceptionResponse.class);
        //Act
        ResponseEntity<String> entityFromApp = restTemplate.postForEntity("http://localhost:" + appPort + "/transfer", transferData, String.class);
        //Assert
        Assertions.assertEquals(HttpStatusCode.valueOf(400), entityFromApp.getStatusCode());
        ExceptionResponse exceptionResponse = objectMapper.readValue(entityFromApp.getBody(), javaType);
        Assertions.assertEquals("Карты не существует", exceptionResponse.getMessage());
    }

    @SneakyThrows
    @Test
    void transferEndpointTest_invalidTransferData_invalidCardCredentials() {
        //Arrange
        TransferData transferData = new TransferData("1111222233334444", "03/24",
                "788", "8888999900001111", new Amount(4000, "RUR"));
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.constructType(ExceptionResponse.class);

        //Act
        ResponseEntity<String> entityFromApp = restTemplate.postForEntity("http://localhost:" + appPort + "/transfer", transferData, String.class);
        //Assert
        Assertions.assertEquals(HttpStatusCode.valueOf(400), entityFromApp.getStatusCode());
        ExceptionResponse exceptionResponse = objectMapper.readValue(entityFromApp.getBody(), javaType);
        Assertions.assertEquals("Неверные данные карты", exceptionResponse.getMessage());
    }

}
