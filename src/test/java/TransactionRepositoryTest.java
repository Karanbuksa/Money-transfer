import com.example.moneytransferproj.entitys.Transaction;
import com.example.moneytransferproj.repository.TransactionRepository;
import com.example.moneytransferproj.repository.parsers.TransactionParser;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionRepositoryTest {


    @InjectMocks
    private TransactionRepository repository = new TransactionRepository(new TransactionParser());

    @Test
    public void getTransactionTest() {
        //Arrange
        Transaction transaction1 = new Transaction("1", "1111111111111111", "2222222222222222", 1000.0, "RUR", 10.0, "Прошла");
        Transaction transaction2 = new Transaction("2", "3333333333333333", "4444444444444444", 500.0, "RUR", 5.0, "Прошла");
        repository.putTransaction(Integer.parseInt(transaction1.getOperationID()), transaction1);
        repository.putTransaction(Integer.parseInt(transaction2.getOperationID()), transaction2);
        //Act
        Transaction receivedTransaction1 = repository.getTransaction(Integer.parseInt(transaction1.getOperationID()));
        Transaction receivedTransaction2 = repository.getTransaction(Integer.parseInt(transaction2.getOperationID()));
        //Assert
        assertEquals(transaction1, receivedTransaction1);
        assertEquals(transaction2, receivedTransaction2);
    }

    @Test
    public void putTransactionTest() {
        //Arrange
        Transaction transaction1 = new Transaction("3", "5555555555555555", "6666666666666666", 2000.0, "RUR", 20.0, "Прошла");
        Transaction transaction2 = new Transaction("4", "7777777777777777", "8888888888888888", 1500.0, "RUR", 15.0, "Прошла");
        //Act
        repository.putTransaction(Integer.parseInt(transaction1.getOperationID()), transaction1);
        repository.putTransaction(Integer.parseInt(transaction2.getOperationID()), transaction2);

        //Assert
        assertEquals(transaction1, repository.getTransaction(Integer.parseInt(transaction1.getOperationID())));
        assertEquals(transaction2, repository.getTransaction(Integer.parseInt(transaction2.getOperationID())));
    }

    @Test
    public void getTransactionAmountTest() {
        //Arrange
        Transaction transaction1 = new Transaction("1", "1111111111111111", "2222222222222222", 1000.0, "USD", 10.0, "Success");
        Transaction transaction2 = new Transaction("2", "3333333333333333", "4444444444444444", 500.0, "EUR", 5.0, "Success");
        Transaction transaction3 = new Transaction("3", "5555555555555555", "6666666666666666", 2000.0, "GBP", 20.0, "Success");
        Transaction transaction4 = new Transaction("4", "7777777777777777", "8888888888888888", 1500.0, "AUD", 15.0, "Success");
        repository.putTransaction(Integer.parseInt(transaction1.getOperationID()), transaction1);
        repository.putTransaction(Integer.parseInt(transaction2.getOperationID()), transaction2);
        repository.putTransaction(Integer.parseInt(transaction3.getOperationID()), transaction3);
        repository.putTransaction(Integer.parseInt(transaction4.getOperationID()), transaction4);

        //Act
        int size = repository.getTransactionAmount();
        //Assert
        assertEquals(size, 4);
    }
}
