import com.example.moneytransferproj.controller.TransferController;
import com.example.moneytransferproj.domain.Transaction;
import com.example.moneytransferproj.exceptions.ConfirmationException;
import com.example.moneytransferproj.exceptions.InputDataException;
import com.example.moneytransferproj.exceptions.TransferException;
import com.example.moneytransferproj.handler.ExceptionHandlerAdvice;
import com.example.moneytransferproj.service.TransferService;
import com.example.moneytransferproj.service.ValidationService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TransferControllerTest {

    @Mock
    private TransferService transferService;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private TransferController transferController;

    private MockMvc mockMvc;

    @SneakyThrows
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(transferController)
                .setControllerAdvice(new ExceptionHandlerAdvice())
                .build();
    }

    @Test
    public void testTransferWithInvalidInputData() throws Exception {
        //Arrange
        doThrow(new InputDataException("Недостаточно средств", new Transaction()))
                .when(transferService).transfer(any());
        //Act
        mockMvc.perform(post("/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cardFromNumber\":\"1111222233334444\",\"cardToNumber\":\"5555666677778888\",\"amount\":{\"value\":100.0,\"currency\":\"USD\"},\"cardFromCVV\":\"123\",\"cardFromValidTill\":\"12/23\"}"))
                //Assert
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Недостаточно средств"))
                .andExpect(jsonPath("$.id").value(3));
    }

    @Test
    public void testConfirmOperationWithInvalidCode() throws Exception {
        //Arrange
        doThrow(new ConfirmationException("Неверный код", new Transaction()))
                .when(validationService).confirm(any(), any());
        //Act
        mockMvc.perform(post("/confirmOperation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"null\"}"))
                //Assert
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Неверный код"))
                .andExpect(jsonPath("$.id").value(3));
    }

    @Test
    public void testTransferWithTransferException() throws Exception {
        //Arrange
        doThrow(new TransferException("Карты не существует", new Transaction()))
                .when(transferService).transfer(any());
        //Act
        mockMvc.perform(post("/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cardFromNumber\":\"1111222233334444\",\"cardToNumber\":\"5555666677778889\",\"amount\":{\"value\":100.0,\"currency\":\"USD\"},\"cardFromCVV\":\"123\",\"cardFromValidTill\":\"12/23\"}"))
                //Assert
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Карты не существует"))
                .andExpect(jsonPath("$.id").value(3));
    }

    @Test
    public void testTransferWithValidData() throws Exception {
        // Arrange
        Transaction testTransaction = new Transaction();
        testTransaction.setOperationID("1");
        Mockito.lenient().when(transferService.transfer(any())).thenReturn(testTransaction);
        Mockito.lenient().when(validationService.confirm(any(), any())).thenReturn("1");

        // Act
        mockMvc.perform(post("/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cardFromNumber\":\"1111222233334444\",\"cardToNumber\":\"5555666677778888\",\"amount\":{\"value\":100.0,\"currency\":\"USD\"},\"cardFromCVV\":\"123\",\"cardFromValidTill\":\"12/23\"}"))
                // Assert
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        // Act
        mockMvc.perform(post("/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\": \"0000\"}"))
                // Assert
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}
