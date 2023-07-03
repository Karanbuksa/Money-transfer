import com.example.moneytransferproj.data_transfer_objects.Amount;
import com.example.moneytransferproj.data_transfer_objects.TransferData;
import com.example.moneytransferproj.entitys.Account;
import com.example.moneytransferproj.entitys.Transaction;
import com.example.moneytransferproj.exceptions.InputDataException;
import com.example.moneytransferproj.repository.CardsRepository;
import com.example.moneytransferproj.repository.TransactionRepository;
import com.example.moneytransferproj.service.TransferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransferServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CardsRepository cardsRepository;

    @InjectMocks
    private TransferService transferService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void transfer_ValidTransferData_ReturnsOperationId() {
        // Arrange
        TransferData transferData = new TransferData("1234567890123456", "12/25",
                "123", "9876543210987654", new Amount(1000, "USD"));


        Account fromAccount = new Account();
        fromAccount.setBalance(2000.0);
        fromAccount.setCvv("123");
        fromAccount.setCardDate("12/25");

        Account toAccount = new Account();
        toAccount.setBalance(1000.0);

        when(cardsRepository.getAccountByCardNumber("1234567890123456")).thenReturn(fromAccount);
        when(cardsRepository.getAccountByCardNumber("9876543210987654")).thenReturn(toAccount);

        // Act
        String operationId = transferService.transfer(transferData).getOperationID();

        // Assert
        assertNotNull(operationId);
        verify(transactionRepository, times(1)).putTransaction(anyInt(), any(Transaction.class));
    }

    @Test
    void transfer_InvalidCard_ReturnsInputDataException() {
        // Arrange
        TransferData transferData = new TransferData("1234567890123456", "12/25",
                "123", "9876543210987654", new Amount(1000, "USD"));

        when(cardsRepository.getAccountByCardNumber("1234567890123456")).thenReturn(null);
        when(cardsRepository.getAccountByCardNumber("9876543210987654")).thenReturn(null);

        // Act & Assert
        assertThrows(InputDataException.class, () -> transferService.transfer(transferData));
        verify(transactionRepository, times(1)).putTransaction(anyInt(), any(Transaction.class));
    }

    @Test
    void transfer_InsufficientBalance_ReturnsInputDataException() {
        // Arrange
        TransferData transferData = new TransferData("1234567890123456", "12/25",
                "123", "9876543210987654", new Amount(300000, "USD"));

        Account fromAccount = new Account();
        fromAccount.setBalance(2000.0);
        fromAccount.setCvv("123");
        fromAccount.setCardDate("12/25");

        Account toAccount = new Account();
        toAccount.setBalance(1000.0);

        when(cardsRepository.getAccountByCardNumber("1234567890123456")).thenReturn(fromAccount);
        when(cardsRepository.getAccountByCardNumber("9876543210987654")).thenReturn(toAccount);

        // Act & Assert
        assertThrows(InputDataException.class, () -> transferService.transfer(transferData));
        verify(transactionRepository, times(1)).putTransaction(anyInt(), any(Transaction.class));
    }

    @Test
    void transfer_InvalidCardData_ReturnsInputDataException() {
        // Arrange
        TransferData transferData = new TransferData("1234567890123456", "12/25",
                "999", "9876543210987654", new Amount(1000, "USD"));

        Account fromAccount = new Account();
        fromAccount.setBalance(2000.0);
        fromAccount.setCvv("123");
        fromAccount.setCardDate("12/25");

        Account toAccount = new Account();
        toAccount.setBalance(1000.0);

        when(cardsRepository.getAccountByCardNumber("1234567890123456")).thenReturn(fromAccount);
        when(cardsRepository.getAccountByCardNumber("9876543210987654")).thenReturn(toAccount);

        // Act & Assert
        assertThrows(InputDataException.class, () -> transferService.transfer(transferData));
        verify(transactionRepository, times(1)).putTransaction(anyInt(), any(Transaction.class));
    }


}
