import com.example.moneytransferproj.entitys.Account;
import com.example.moneytransferproj.repository.AccountsRepository;
import com.example.moneytransferproj.repository.parsers.CardsFileParser;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AccountsRepositoryTest {
    @Mock
    private CardsFileParser parser = new CardsFileParser();
    @InjectMocks
    private AccountsRepository repository = new AccountsRepository(parser);

    @Test
    public void testGetAccountByCardNumber() {
        // Arrange
        Account expectedAccount = new Account("1234567890123456", "12/25", "333", 1000.0);

        // Act
        Account actualAccount = repository.getAccountByCardNumber(expectedAccount.getCardNumber());

        // Assert
        assertEquals(expectedAccount, actualAccount);
    }

    @Test
    public void testAddAccount() {
        // Arrange
        Account account = new Account("1234123412341234", "12/25", "333", 1000.0);

        // Act
        repository.addAccount(account);

        // Assert
        assertEquals(account, repository.getAccountByCardNumber(account.getCardNumber()));
    }

    @Test
    public void testUpdateCards() {
        // Arrange
        Account account1 = new Account("1234123412341234", "12/25", "333", 1000.0);
        Account account2 = new Account("2345234523452345", "12/25", "333", 500.0);
        CardsFileParser cardsFileParser = new CardsFileParser();
        repository.addAccount(account1);
        repository.addAccount(account2);
        // Act
        repository.updateAccounts();
        ConcurrentHashMap<String, Account> accounts = cardsFileParser.readCardsFromFile();
        // Assert
        assertEquals(accounts.get("1234123412341234"), account1);
        assertEquals(accounts.get("2345234523452345"), account2);
    }

    @Test
    public void testRemoveAccount() {
        //Arrange
        Account account1 = new Account("1234123412341234", "12/25", "333", 1000.0);
        Account account2 = new Account("2345234523452345", "12/25", "333", 500.0);
        CardsFileParser cardsFileParser = new CardsFileParser();
        //Act
        repository.removeAccountByCardNumber(account1.getCardNumber());
        repository.removeAccountByCardNumber(account2.getCardNumber());
        repository.updateAccounts();
        ConcurrentHashMap<String, Account> accounts = cardsFileParser.readCardsFromFile();
        //Assert
        assertFalse(accounts.contains(account1));
        assertFalse(accounts.contains(account2));
    }
}
