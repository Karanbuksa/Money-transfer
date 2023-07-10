import com.example.moneytransferproj.data_transfer_objects.ConfirmOperation;
import com.example.moneytransferproj.entitys.Account;
import com.example.moneytransferproj.entitys.Transaction;
import com.example.moneytransferproj.exceptions.ConfirmationException;
import com.example.moneytransferproj.repository.AccountsRepository;
import com.example.moneytransferproj.service.ValidationService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ValidationServiceTest {

    @Mock
    private AccountsRepository accountsRepository;
    @InjectMocks
    private ValidationService validationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SneakyThrows
    @Test
    void confirm_ValidConfirmOperation_PerformsTransaction() {
        // Arrange
        ConfirmOperation confirmOperation = new ConfirmOperation("1234");


        Account fromAccount = new Account();
        fromAccount.setCardNumber("1234567890123456");
        fromAccount.setBalance(2000.0);

        Account toAccount = new Account();
        toAccount.setCardNumber("9876543210987654");
        toAccount.setBalance(1000.0);

        Transaction transaction = new Transaction("5", "1234567890123456",
                "9876543210987654", 300.0, "RUR", 3.0, "");

        when(accountsRepository.getAccountByCardNumber("1234567890123456")).thenReturn(fromAccount);
        when(accountsRepository.getAccountByCardNumber("9876543210987654")).thenReturn(toAccount);

        // Act
        String operationId = validationService.confirm(confirmOperation, transaction);

        // Assert
        assertNotNull(operationId);

        verify(accountsRepository, times(1)).updateAccounts();

        assertEquals("Проведена", transaction.getOperationResult());
    }


    @Test
    void confirm_InvalidCode_ThrowsConfirmationException() {
        // Arrange
        ConfirmOperation confirmOperation = new ConfirmOperation(null);
        Transaction transaction = new Transaction("5", "1234567890123456",
                "9876543210987654", 300.0, "RUR", 3.0, "");
        // Assert
        assertThrows(ConfirmationException.class, () -> validationService.confirm(confirmOperation, transaction));
        verify(accountsRepository, never()).updateAccounts();
    }
}
