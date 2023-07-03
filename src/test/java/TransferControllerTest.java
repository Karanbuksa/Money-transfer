import com.example.moneytransferproj.controller.TransferController;
import com.example.moneytransferproj.data_transfer_objects.ConfirmOperation;
import com.example.moneytransferproj.data_transfer_objects.TransferData;
import com.example.moneytransferproj.entitys.Transaction;
import com.example.moneytransferproj.exceptions.InputDataException;
import com.example.moneytransferproj.service.TransferService;
import com.example.moneytransferproj.service.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TransferControllerTest {

    @Mock
    private TransferService transferService;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private TransferController transferController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transferController).build();
    }

    @Test
    void transfer_ValidTransferData_ReturnsOperationID() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setOperationID("1");
        when(transferService.transfer(any(TransferData.class))).thenReturn(transaction);

        mockMvc.perform(MockMvcRequestBuilders.post("/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cardFromNumber\": \"9999000011112222\", \"cardFromValidTill\": \"11/23\", \"cardFromCVV\": \"345\", \"cardToNumber\": \"5555666677778888\", \"amount\": {\"value\": 100, \"currency\": \"USD\"}}"))
                .andExpect(status().isOk());
    }


    @Test
    void transfer_InvalidTransferData_ReturnsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cardFromNumber\": \"9999000011112222\", \"cardFromValidTill\": \"11/23\", \"cardFromCVV\": \"345\", \"cardToNumber\": \"5555666677778888\", \"amount\": {\"value\": -100, \"currency\": \"USD\"}}"))
                .andExpect(status().isBadRequest());
    }


    @Test
    void confirmOperation_ValidConfirmData_ReturnsOperationID() throws Exception {
        ConfirmOperation confirmOperation = new ConfirmOperation("1234");
        Transaction transaction = new Transaction();
        transaction.setOperationID("1");
        when(validationService.confirm(any(ConfirmOperation.class), any(Transaction.class))).thenReturn(transaction.getOperationID());

        mockMvc.perform(MockMvcRequestBuilders.post("/confirmOperation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\": \"1234\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void confirmOperation_InvalidConfirmData_ReturnsBadRequest() throws Exception {
        doThrow(new InputDataException("Invalid confirmation code", new Transaction())).when(validationService).confirm(any(ConfirmOperation.class), any(Transaction.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/confirmOperation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\": \"5678\"}"))
                .andExpect(status().isBadRequest());
    }
}
