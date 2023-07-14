import com.example.moneytransferproj.domain.Account;
import com.example.moneytransferproj.repository.parsers.CardsFileParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardsFileParserTest {
    @InjectMocks
    private CardsFileParser parser = new CardsFileParser();

    @BeforeEach
    public void setUp() {
        parser.setPath("src/test/resources/cards.json");
    }

    @Test
    public void readCardsFromFileTest() {
        //Arrange
        Account account1 = new Account("9999000011112222", "11/23", "345", 759.6);
        Account account2 = new Account("5555666677778888", "07/22", "012", 240.0);
        Account account3 = new Account("0987654321098765", "12/25", "333", 500.0);
        //Act
        ConcurrentHashMap<String, Account> accountConcurrentHashMap = parser.readCardsFromFile();
        //Assert
        assertEquals(accountConcurrentHashMap.get("9999000011112222"), account1);
        assertEquals(accountConcurrentHashMap.get("5555666677778888"), account2);
        assertEquals(accountConcurrentHashMap.get("0987654321098765"), account3);
    }

    @Test
    public void jsonToListTest() {
        //Arrange
        Account account1 = new Account("9999000011112222", "11/23", "345", 759.6);
        Account account2 = new Account("5555666677778888", "07/22", "012", 240.0);
        Account account3 = new Account("0987654321098765", "12/25", "333", 500.0);
        //Act
        List<Account> accounts = parser.jsonToList();
        //Assert
        assertThat(accounts, hasItems(account1, account2, account3));
    }

    @Test
    public void listToJsonTest() {
        //Arrange
        Account account1 = new Account("9999000011112222", "11/23", "345", 759.6);
        Account account2 = new Account("5555666677778888", "07/22", "012", 240.0);
        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);
        //Act
        String json = parser.listToJson(accounts);
        //
        assertThat(json, is("""
                [
                  {
                    "cardNumber": "9999000011112222",
                    "cardDate": "11/23",
                    "cvv": "345",
                    "balance": 759.6
                  },
                  {
                    "cardNumber": "5555666677778888",
                    "cardDate": "07/22",
                    "cvv": "012",
                    "balance": 240.0
                  }
                ]"""));
    }

    @Test
    public void testWriteString() {
        // Arrange
        String expected = "Test string";
        parser.setPath("src/test/resources/temp.txt");
        // Act
        parser.writeString(expected);

        // Assert
        try {
            String actual = Files.readString(Path.of("src/test/resources/temp.txt"));
            assertEquals(expected, actual);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
