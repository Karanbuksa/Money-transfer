import com.example.moneytransferproj.controller.TransferController;
import com.example.moneytransferproj.dataclasses.Amount;
import com.example.moneytransferproj.dataclasses.ConfirmOperation;
import com.example.moneytransferproj.dataclasses.TransferData;
import com.example.moneytransferproj.service.TransferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransferControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TransferService transferService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        TransferController transferController = new TransferController(transferService);
        mockMvc = MockMvcBuilders.standaloneSetup(transferController).build();
    }

    @Test
    public void testTransfer() throws Exception {
        TransferData transferData = new TransferData();

        transferData.setAmount(new Amount(40000,"RUR"));
        transferData.setCardFromNumber("1234123412341234");
        transferData.setCardFromCVV("333");
        transferData.setCardToNumber("4567456745674567");
        transferData.setCardFromValidTill("12/33 ");

        mockMvc.perform(post("/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(transferData)))
                .andExpect(status().isOk());

        verify(transferService).transfer(transferData);
    }

    @Test
    public void testConfirmOperation() throws Exception {
        ConfirmOperation confirmOperation = new ConfirmOperation();

        confirmOperation.setCode("0000");

        mockMvc.perform(post("/confirmOperation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(confirmOperation)))
                .andExpect(status().isOk());

        verify(transferService).confirm(confirmOperation);
    }

    private static String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
