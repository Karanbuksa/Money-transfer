import com.example.moneytransferproj.dataclasses.*;
import com.example.moneytransferproj.exceptions.ConfirmationException;
import com.example.moneytransferproj.exceptions.InputDataException;
import com.example.moneytransferproj.repository.CardsRepository;
import com.example.moneytransferproj.repository.TransactionRepository;
import com.example.moneytransferproj.service.TransferService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;

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
        TransferData transferData = new TransferData();
        transferData.setCardFromNumber("1234567890123456");
        transferData.setCardToNumber("9876543210987654");
        transferData.setCardFromCVV("123");
        transferData.setCardFromValidTill("12/25");
        transferData.setAmount(new Amount(1000, "USD"));

        Account fromAccount = new Account();
        fromAccount.setBalance(2000.0);
        fromAccount.setCVV("123");
        fromAccount.setCardDate("12/25");

        Account toAccount = new Account();
        toAccount.setBalance(1000.0);

        when(cardsRepository.getAccountByCardNumber("1234567890123456")).thenReturn(fromAccount);
        when(cardsRepository.getAccountByCardNumber("9876543210987654")).thenReturn(toAccount);

        // Act
        String operationId = transferService.transfer(transferData);

        // Assert
        assertNotNull(operationId);
        verify(transactionRepository, times(1)).putTransaction(anyInt(), any(Transaction.class));
    }

    @Test
    void transfer_InvalidCard_ReturnsInputDataException() {
        // Arrange
        TransferData transferData = new TransferData();
        transferData.setCardFromNumber("1234567890123456");
        transferData.setCardToNumber("9876543210987654");
        transferData.setCardFromCVV("123");
        transferData.setCardFromValidTill("12/25");
        transferData.setAmount(new Amount(1000, "USD"));

        when(cardsRepository.getAccountByCardNumber("1234567890123456")).thenReturn(null);
        when(cardsRepository.getAccountByCardNumber("9876543210987654")).thenReturn(null);

        // Act & Assert
        assertThrows(InputDataException.class, () -> transferService.transfer(transferData));
        verify(transactionRepository, times(1)).putTransaction(anyInt(), any(Transaction.class));
    }

    @Test
    void transfer_InsufficientBalance_ReturnsInputDataException() {
        // Arrange
        TransferData transferData = new TransferData();
        transferData.setCardFromNumber("1234567890123456");
        transferData.setCardToNumber("9876543210987654");
        transferData.setCardFromCVV("123");
        transferData.setCardFromValidTill("12/25");
        transferData.setAmount(new Amount(300000, "USD"));

        Account fromAccount = new Account();
        fromAccount.setBalance(2000.0);
        fromAccount.setCVV("123");
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
        TransferData transferData = new TransferData();
        transferData.setCardFromNumber("1234567890123456");
        transferData.setCardToNumber("9876543210987654");
        transferData.setCardFromCVV("999");
        transferData.setCardFromValidTill("12/25");
        transferData.setAmount(new Amount(1000, "USD"));

        Account fromAccount = new Account();
        fromAccount.setBalance(2000.0);
        fromAccount.setCVV("123");
        fromAccount.setCardDate("12/25");

        Account toAccount = new Account();
        toAccount.setBalance(1000.0);

        when(cardsRepository.getAccountByCardNumber("1234567890123456")).thenReturn(fromAccount);
        when(cardsRepository.getAccountByCardNumber("9876543210987654")).thenReturn(toAccount);

        // Act & Assert
        assertThrows(InputDataException.class, () -> transferService.transfer(transferData));
        verify(transactionRepository, times(1)).putTransaction(anyInt(), any(Transaction.class));
    }

    @SneakyThrows
    @Test
    void confirm_ValidConfirmOperation_PerformsTransaction() {
        // Arrange
        ConfirmOperation confirmOperation = new ConfirmOperation();
        confirmOperation.setCode("1234");

        Account fromAccount = new Account();
        fromAccount.setCardNumber("1234567890123456");
        fromAccount.setBalance(2000.0);

        Account toAccount = new Account();
        toAccount.setCardNumber("9876543210987654");
        toAccount.setBalance(1000.0);

        Transaction transaction = new Transaction("5", "1234567890123456", "9876543210987654", 300.0, "RUR", 3.0, "");

        when(cardsRepository.getAccountByCardNumber("1234567890123456")).thenReturn(fromAccount);
        when(cardsRepository.getAccountByCardNumber("9876543210987654")).thenReturn(toAccount);
        Field field = TransferService.class.getDeclaredField("transactionBuffer");
        field.setAccessible(true);
        field.set(null, transaction);
        // Act
        String operationId = transferService.confirm(confirmOperation);

        // Assert
        assertNotNull(operationId);

        verify(cardsRepository, times(1)).updateCards();

        assertEquals("Проведена", transaction.getOperationResult());
    }


    @Test
    void confirm_InvalidCode_ThrowsConfirmationException() {
        // Arrange
        ConfirmOperation confirmOperation = new ConfirmOperation();
        confirmOperation.setCode(null);

        // Act & Assert
        assertThrows(ConfirmationException.class, () -> transferService.confirm(confirmOperation));
        verify(cardsRepository, never()).updateCards();
    }
}
