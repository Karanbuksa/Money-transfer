import com.example.moneytransferproj.domain.Transaction;
import com.example.moneytransferproj.repository.parsers.TransactionParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TransactionParserTest {
    @InjectMocks
    private TransactionParser parser = new TransactionParser();

    @BeforeEach
    public void setUp() {
        parser.setPath("src/test/resources/transfer.log");
    }

    @Test
    public void parseTransactionTest_expectTransaction() {
        //Arrange
        Transaction expectedTransaction = new Transaction("1",
                "1111222233334444",
                "8888999900001111",
                0,
                null,
                0,
                "Не проведена: неверные данные карты");
        String testString = "04-07-2023 22:03:57.816 - ID операции: 1," +
                " Номер карты списания: 1111222233334444," +
                " Номер карты зачисления: 8888999900001111," +
                " Результат операции: Не проведена: неверные данные карты";
        //Act
        Transaction actualTransaction = parser.parseTransaction(testString);
        //Assert
        assertThat(expectedTransaction, is(actualTransaction));
    }

    @Test
    public void parseTransactionTest_expectNull() {
        //Arrange
        String testString = "04-07-2023 22:03:57.816 - ID операции: 1," +
                " Номер карты списания: 1111222233334444," +
                " Номер карты зачисления: 8888999900001111, " +
                " Результат операции: Не проведена: неверные данные карты";
        //Act
        Transaction actualTransaction = parser.parseTransaction(testString);
        //Assert
        assertNull(actualTransaction);
    }

    @Test
    public void readTransactionsFromFileTest() {
        //Arrange
        Transaction transaction1 = new Transaction("1", "1111222233334444", "8888999900001111", 0, null, 0, "Не проведена: Неверные данные карты");
        Transaction transaction2 = new Transaction("2", "9999000011112222", "5555666677778888", 0, null, 0, "Проведена");
        Transaction transaction3 = new Transaction("3", "1234123412341234", "1234234523452345", 0, null, 0, "Не проведена: Карты не существует");

        //Act
        ConcurrentHashMap<Integer, Transaction> transactionConcurrentHashMap = parser.readTransactionsFromFile();
        List<Transaction> transactions = transactionConcurrentHashMap.values().stream().toList();
        //Assert
        assertThat(transactions, hasItems(transaction1, transaction2, transaction3));
    }
}
